package org.tagaprice.server.dao.postgres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.DAOException;

public class ReceiptDAO implements IReceiptDAO {
	private DBConnection _db;
	private EntityDAO _entityDAO;
	private ShopDAO _shopDAO;
	private ProductDAO _productDAO;
	private static Logger _log = Logger.getLogger(ReceiptDAO.class);
	
	public ReceiptDAO(DBConnection db) {
		this._db=db;
		_entityDAO = new EntityDAO(db);
		_shopDAO = new ShopDAO(db);
		_productDAO = new ProductDAO(db);
	}

	@Override
	public List<ReceiptData> getUserReceipts(long id) throws DAOException {
		_log.debug("id:"+id);
		String sql = "SELECT rid FROM " +
				"receipt re " +
				"INNER JOIN entity en " +
				"ON (en.ent_id=re.rid) " +
				"INNER JOIN entityrevision enr " +
				"ON (en.current_revision=enr.rev AND en.ent_id=enr.ent_id) " +
				"WHERE en.creator=? ";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, id);
			ResultSet resSet = pstmt.executeQuery();
			List<ReceiptData> receipts = new ArrayList<ReceiptData>();
			while(resSet.next()){
				receipts.add(getById(resSet.getLong("rid")));
			}
			return receipts;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
	
	
	@Override
	public ReceiptData getById(long id) throws DAOException {
		_log.debug("id:"+id);
		//get Entity Data
		ReceiptData receipt = _entityDAO.getById(new ReceiptData(), id);
		if(receipt == null)
			return null;
		receipt.setDate(new Date());
		receipt.setProductData(new ArrayList<ProductData>());
		receipt.setDraft(true);
			
		
		//GetReceiptData
		String sql = "SELECT sid, re.created_at, draft " +
				"FROM receipt re " +
				"INNER JOIN entity en " +
				"ON re.rid=en.ent_id " +
				"WHERE en.creator = ? AND re.rid = ?";
		PreparedStatement pstmt;
		try {
			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, receipt.getCreatorId());
			pstmt.setLong(2, receipt.getId());
			ResultSet resSet = pstmt.executeQuery();

			if(!resSet.next()) {
				return null;
			}

			receipt.setDate(resSet.getDate("created_at"));
			receipt.setDraft(resSet.getBoolean("draft"));

			//Get shop
			if(resSet.getLong("sid")>0){
				ShopData shopTemp = _shopDAO.getById(resSet.getLong("sid"));
				if(shopTemp == null)
					return null;
				receipt.setShop(shopTemp);
			}

			//Get Products
			sql = "SELECT ree.pid, ree.price " +
			"FROM receipt re " +
			"INNER JOIN entity en " +
			"ON re.rid=en.ent_id " +
			"INNER JOIN receiptentry ree " +
			"ON ree.rid = re.rid " +
			"WHERE en.creator = ? AND re.rid = ?";

			pstmt = _db.prepareStatement(sql);
			pstmt.setLong(1, receipt.getCreatorId());
			pstmt.setLong(2, receipt.getId());
			ResultSet resSet2 = pstmt.executeQuery();

			ArrayList<ProductData> productDataArray = new ArrayList<ProductData>();

			while(resSet2.next()){
				ProductData tempProduct = _productDAO.getById(resSet2.getLong("pid"));
				tempProduct.setAvgPrice(new Price(resSet2.getInt("price"), 4, 1, "€", 1));
				tempProduct.setQuantity(new Quantity(1, new Unit(23, 2, "kg", 1, null, 0)));			
				productDataArray.add(tempProduct);
			}


			receipt.setProductData(productDataArray);
			return receipt;
		} catch (SQLException e) {
			String msg = "Failed to retrieve shop. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}

	@Override
	public boolean save(ReceiptData receipt) throws DAOException {
		_log.debug("receipt:"+receipt);
		//new Entity
		if(!_entityDAO.save(receipt))
			return false;

		PreparedStatement pstmt;
		try {
			if(receipt.getRev()==1){
				//create new Receipt
				pstmt = _db.prepareStatement("INSERT INTO receipt (rid) VALUES (?)");
				pstmt.setLong(1, receipt.getId());
				pstmt.executeUpdate();
			} else if (receipt.getRev() < 1){ 
				throw new DAOException("EntityDAO returned shop with revision < 0!");
			} else if (receipt.getRev() > 1){
				pstmt = _db.prepareStatement("UPDATE receipt SET " +
						"sid = ?, " +
						"draft = ?, " +
						"created_at = ? " +
				"WHERE rid = ?");

				//Set Shop
				if(receipt.getShop()==null) 
					pstmt.setNull(1, Types.BIGINT);
				else 
					pstmt.setLong(1, receipt.getShop().getId());

				//setDraft
				if(receipt.getDraft()) 
					pstmt.setBoolean(2, true);
				else 
					pstmt.setBoolean(2, false);

				//setDate
				if(receipt.getDate()==null) 
					pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
				else 
					pstmt.setDate(3, new java.sql.Date(receipt.getDate().getTime()));

				//set WHERE 
				pstmt.setLong(4, receipt.getId());
				pstmt.executeUpdate();



				//Set Price
				//Remove old
				String sql = "DELETE FROM receiptentry " +
				"WHERE rid = ?";
				pstmt = _db.prepareStatement(sql);
				pstmt.setLong(1, receipt.getId());
				pstmt.executeUpdate();

				//Add new
				for(ProductData pd:receipt.getProductData()){
					pstmt = _db.prepareStatement("INSERT INTO receiptentry " +
					"(rid, pid, price) VALUES (?,?,?)");

					pstmt.setLong(1, receipt.getId());
					pstmt.setLong(2, pd.getId());
					pstmt.setLong(3, pd.getAvgPrice().getPrice());

					pstmt.executeUpdate();
				}

			}		
			return true;
		} catch (SQLException e) {
			String msg = "Failed to save receipt. SQLException: "+e.getMessage()+".";
			_log.error(msg+" Chaining and rethrowing.");
			_log.debug(e.getStackTrace());
			throw new DAOException(msg, e);
		}
	}
}

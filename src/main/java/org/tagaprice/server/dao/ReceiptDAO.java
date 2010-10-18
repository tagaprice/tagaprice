package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class ReceiptDAO implements DAOClass<ReceiptData> {
	private DBConnection db;
	private EntityDAO entityDAO;
	private ShopDAO shopDAO;
	private ProductDAO productDAO;
	
	public ReceiptDAO(DBConnection db) {
		this.db=db;
		entityDAO = new EntityDAO(db);
		shopDAO = new ShopDAO(db);
		productDAO = new ProductDAO(db);
	}

	public void getUserReceipts(ArrayList<ReceiptData> receipt, long uid) throws SQLException, NotFoundException{
		PreparedStatement pstmt;
		
		String sql = "SELECT rid FROM " +
				"receipt re " +
				"INNER JOIN entity en " +
				"ON (en.ent_id=re.rid) " +
				"INNER JOIN entityrevision enr " +
				"ON (en.current_revision=enr.rev AND en.ent_id=enr.ent_id) " +
				"WHERE en.creator=? ";
		
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, uid);
		
		ResultSet resSet = pstmt.executeQuery();
		
		while(resSet.next()){
			ReceiptData temp = new ReceiptData(resSet.getLong("rid"));
			get(temp);
			receipt.add(temp);
		}
	}
	
	
	@Override
	public void get(ReceiptData receipt) throws SQLException, NotFoundException {
		PreparedStatement pstmt;
		//get Entity Data
		entityDAO.get(receipt);	
		
		
		receipt.setDate(new Date());
		receipt.setProductData(new ArrayList<ProductData>());
		receipt.setDraft(true);
			
		
		//GetReceiptData
		String sql = "SELECT sid, re.created_at, draft " +
				"FROM receipt re " +
				"INNER JOIN entity en " +
				"ON re.rid=en.ent_id " +
				"WHERE en.creator = ? AND re.rid = ?";
		
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, receipt.getCreatorId());
		pstmt.setLong(2, receipt.getId());
		ResultSet resSet = pstmt.executeQuery();
		
		if(!resSet.next()) throw new NotFoundException("Receipt not found.");
		
		receipt.setDate(resSet.getDate("created_at"));
		receipt.setDraft(resSet.getBoolean("draft"));
		
		//Get shop
		if(resSet.getLong("sid")>0){
			ShopData sTemp = new ShopData(resSet.getLong("sid"));
			shopDAO.get(sTemp);
			receipt.setShop(sTemp);
		}
		
		//Get Products
		sql = "SELECT ree.pid, ree.price " +
		"FROM receipt re " +
		"INNER JOIN entity en " +
		"ON re.rid=en.ent_id " +
		"INNER JOIN receiptentry ree " +
		"ON ree.rid = re.rid " +
		"WHERE en.creator = ? AND re.rid = ?";
		
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, receipt.getCreatorId());
		pstmt.setLong(2, receipt.getId());
		ResultSet resSet2 = pstmt.executeQuery();

		ArrayList<ProductData> productDataArray = new ArrayList<ProductData>();
		
		while(resSet2.next()){
			ProductData tempProduct = new ProductData(resSet2.getLong("pid"));
			productDAO.get(tempProduct);
			tempProduct.setAvgPrice(new Price(resSet2.getInt("price"), 4, 1, "€", 1));
			tempProduct.setQuantity(new Quantity(1, new Unit(23, 2, "kg", 1, null, 0)));			
			productDataArray.add(tempProduct);
		}
		
		
		receipt.setProductData(productDataArray);
	}

	@Override
	public void save(ReceiptData receipt) throws SQLException,
			NotFoundException, RevisionCheckException, InvalidLocaleException {
		PreparedStatement pstmt;
		
		//new Entity
		entityDAO.save(receipt);
		
		if(receipt.getRev()==1){
			//create new Receipt
			pstmt = db.prepareStatement("INSERT INTO receipt (rid) VALUES (?)");
			pstmt.setLong(1, receipt.getId());
			pstmt.executeUpdate();
		}
		else if (receipt.getRev() < 1){ 
			throw new RevisionCheckException("invalid shop revision: "+receipt.getRev());
		}else if (receipt.getRev() > 1){
			pstmt = db.prepareStatement("UPDATE receipt SET " +
					"sid = ?, " +
					"draft = ?, " +
					"created_at = ? " +
					"WHERE rid = ?");
			
			//Set Shop
			if(receipt.getShop()==null) pstmt.setNull(1, Types.BIGINT);
			else pstmt.setLong(1, receipt.getShop().getId());
			
			//setDraft
			if(receipt.getDraft()) pstmt.setBoolean(2, true);
			else pstmt.setBoolean(2, false);
			
			//setDate
			if(receipt.getDate()==null) pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
			else pstmt.setDate(3, new java.sql.Date(receipt.getDate().getTime()));
			
			//set WHERE 
			pstmt.setLong(4, receipt.getId());
			pstmt.executeUpdate();
			
			
			
			//Set Price
			//Remove old
			String sql = "DELETE FROM receiptentry " +
					"WHERE rid = ?";
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, receipt.getId());
			pstmt.executeUpdate();
			
			//Add new
			for(ProductData pd:receipt.getProductData()){
				pstmt = db.prepareStatement("INSERT INTO receiptentry " +
						"(rid, pid, price) VALUES (?,?,?)");
				
				pstmt.setLong(1, receipt.getId());
				pstmt.setLong(2, pd.getId());
				pstmt.setLong(3, pd.getAvgPrice().getPrice());
				
				pstmt.executeUpdate();
			}
			
		}		
		
	}
	
	
	
}

package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.Country;
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
	private CountryDAO countryDAO;
	private ShopDAO shopDAO;
	
	public ReceiptDAO(DBConnection db) {
		this.db=db;
		entityDAO = new EntityDAO(db);
		countryDAO = new CountryDAO(db);
		shopDAO = new ShopDAO(db);
	}

	@Override
	public void get(ReceiptData receipt) throws SQLException, NotFoundException {
		PreparedStatement pstmt;
		//get Entity Data
		entityDAO.get(receipt);
		System.out.println("getReceipt: "+receipt.getId());
		
		
		
		receipt.setDate(new Date());
		receipt.setProductData(new ArrayList<ProductData>());
		receipt.setDraft(true);
		
		

		
		
		
		//GetReceiptData
		String sql = "SELECT * " +
				"FROM receipt re " +
				"INNER JOIN entity en " +
				"ON re.rid=en.ent_id " +
				"WHERE en.creator = ? AND re.rid = ?";
		
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, receipt.getCreatorId());
		pstmt.setLong(2, receipt.getId());
		ResultSet resSet = pstmt.executeQuery();
		
		if(!resSet.next()) throw new NotFoundException("Receipt not found.");
		
		receipt.setDraft(resSet.getBoolean("draft"));
		
		//Get shop
		System.out.println("sid: "+resSet.getLong("sid"));
		if(resSet.getLong("sid")>0){
			ShopData sTemp = new ShopData(resSet.getLong("sid"));
			receipt.setShop(sTemp);
			
		}
		
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
					"sid = ?," +
					"draft = ?");
			
			//Set Shop
			if(receipt.getShop()==null) pstmt.setNull(1, Types.BIGINT);
			else pstmt.setLong(1, receipt.getShop().getId());
			
			//setDraft
			if(receipt.getDraft()) pstmt.setBoolean(2, true);
			else pstmt.setBoolean(2, false);
			
			//Set Price
			//...
			
			
			pstmt.executeUpdate();
		}

		
		
	}
	
	
	
}

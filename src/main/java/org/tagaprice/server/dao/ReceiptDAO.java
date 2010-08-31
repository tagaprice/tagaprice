package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class ReceiptDAO implements DAOClass<ReceiptData> {
	private DBConnection db;
	private EntityDAO entityDAO;
	private CountryDAO countryDAO;
	
	public ReceiptDAO(DBConnection db) {
		this.db=db;
		entityDAO = new EntityDAO(db);
		countryDAO = new CountryDAO(db);
	}

	@Override
	public void get(ReceiptData receipt) throws SQLException, NotFoundException {
		// TODO Auto-generated method stub
		
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
		}

	}
	
	
	
}

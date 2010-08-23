package org.tagaprice.server.dao;

import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.AccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class LocalAccountDAO implements DAOClass<AccountData>{
	private DBConnection db;
	private static LocalAccountDAO instance;
	
	public static LocalAccountDAO getInstance(DBConnection db){
		if(instance == null)
			instance = new LocalAccountDAO(db);
		
		return instance;
	}
	
	private LocalAccountDAO(DBConnection db) {
		this.db=db;
	}
	
	@Override
	public void get(AccountData entity) throws SQLException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(AccountData entity) throws SQLException,
			NotFoundException, RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		
	}

}

package org.tagaprice.server.dao;

import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.LocalAccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class LocalAccountDAO implements DAOClass<LocalAccountData>{
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
	
	
	public boolean isEmailEvalable(String email){
		
		return false;
	}
	
	@Override
	public void get(LocalAccountData entity) throws SQLException, NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(LocalAccountData entity) throws SQLException,
			NotFoundException, RevisionCheckException, InvalidLocaleException {
		// TODO Auto-generated method stub
		
	}

}

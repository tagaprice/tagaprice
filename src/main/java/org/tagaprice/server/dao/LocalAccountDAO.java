package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	
	public boolean isEmailEvalable(String email)throws SQLException, NotFoundException, NotFoundException{
		if(!email.trim().matches(".+@.+\\.[a-z]+")){
			return false;
		}
		
		
		String sql = "SELECT *  FROM localaccount WHERE (email = ?)";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, email);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()){
			return true;
		}
		return false;
	}
	
	public boolean isUsernameEvalabel(String username) throws SQLException, NotFoundException, NotFoundException{
		String sql = "" +
				"SELECT * FROM account " +
				"INNER JOIN entity " +
				"ON (entity.ent_id = account.uid) " +
				"INNER JOIN entityrevision " +
				"ON (entity.current_revision = entityrevision.rev " +
				"AND entity.ent_id = entityrevision.ent_id) " +
				"WHERE (entityrevision.title = ?)";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, username);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()){
			return true;
		}
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

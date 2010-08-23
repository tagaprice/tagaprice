package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.AccountData;
import org.tagaprice.shared.LocalAccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class LocalAccountDAO implements DAOClass<LocalAccountData>{
	private DBConnection db;
	private static LocalAccountDAO instance;
	private static AccountDAO aDao;
	private Long userId = 1l;
	
	private EntityDAO entityDAO;
	
	public static LocalAccountDAO getInstance(DBConnection db){
		if(instance == null){
			instance = new LocalAccountDAO(db);
		}
		return instance;
	}
	
	private LocalAccountDAO(DBConnection db) {
		this.db=db;
		aDao = AccountDAO.getInstance(db);
		entityDAO = EntityDAO.getInstance(db);
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
		
		
		
		//Create or update account
		AccountData aData;
		//aData = new AccountData(entity.getTitle(), entity.getLocaleId());
		//System.out.println("oldid: "+aData.getId());
		//entityDAO.save(aData);
		
		
		
		
		if(entity.getCreatorId()==null){
			
			aData = new AccountData(entity.getTitle(), entity.getLocaleId());
			System.out.println("newid: "+aData.getId());
		}else{
			//TODO Change UserId to loggedin User
			aData = new AccountData(entity.getId(), entity.getRev(), entity.getTitle(), userId);
			System.out.println("updateid: "+aData.getId());
		}		
		aDao.save(aData);
		
		
		//Create or Update LocalAccount
	}

}

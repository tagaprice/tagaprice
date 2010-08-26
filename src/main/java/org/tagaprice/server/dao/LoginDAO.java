package org.tagaprice.server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;

public class LoginDAO {

	private DBConnection db;
	private String session=null;
	
	public LoginDAO(DBConnection db) {
		this.db=db;
	}
	
	public String login(String username, String password) throws SQLException{	
		
		String sql = "" +
				"SELECT la.uid, locale_id, er.title, la.password, la.salt " +
				"FROM localaccount la " +
				"INNER JOIN account ac " +
				"ON la.uid=ac.uid " +
				"INNER JOIN entity en " +
				"ON en.ent_id=la.uid " +
				"INNER JOIN entityrevision er " +
				"ON en.current_revision=er.rev AND en.ent_id=er.ent_id " +
				"WHERE ac.locked='false' " +
				"AND er.title = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, username);
		ResultSet res = pstmt.executeQuery();
		
		
		if(username.equals("test") && password.equals("test")){
			return session;
		}
	
		return null;
	}
	
	public Long getId(String sid) throws IllegalArgumentException {
		
		if(sid.equals(session))
			return 8l;
		
		return null;
	}
	
	public boolean logout(String sid) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}
}

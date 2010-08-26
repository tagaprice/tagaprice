package org.tagaprice.server.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;

public class LoginDAO {

	private DBConnection db;
	private Long id=null;
	
	public LoginDAO(DBConnection db) {
		this.db=db;
	}
	
	public String login(String username, String password) throws SQLException, NoSuchAlgorithmException{	
		
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
		
		if(!res.next()) return null;
			
		String salt = res.getString("salt");
		String pwdHash = res.getString("password");	
		
		if(md5(password+salt).equals(pwdHash)){		
			String session = md5("username"+Math.random());
			
			//TODO check if sid is in use
			
			sql = "INSERT INTO session " +
					"(uid, sid) " +
					"VALUES " +
					"(?,?)";
			
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, res.getLong("uid"));
			pstmt.setString(2, session);
			pstmt.executeUpdate();
			
			
			id = res.getLong("uid");
			return session;
		}
		
		return null;
	}
	
	public Long getId(String sid) throws IllegalArgumentException, SQLException {
		String sql = "SELECT uid FROM session " +
				"WHERE sid = ? "; 
		
		//TODO Check time slice.
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, sid);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()) return null;
		
		return res.getLong("uid");
	}
	
	public boolean logout(String sid) throws IllegalArgumentException, SQLException {
		
		String sql = "DELETE FROM session " +
				"WHERE " +
				"uid = ? AND sid = ?";
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, getId(sid));
		pstmt.setString(2, sid);
		ResultSet res = pstmt.executeQuery();
		
		if(res.next())return true;
				
		return false;
	}
	
	public static String md5(String in) throws NoSuchAlgorithmException {
		// calculate hash 
		MessageDigest md5 = MessageDigest.getInstance("MD5");
	    md5.update(in.getBytes());
	    byte[] hash = md5.digest();
	    StringBuffer rc = new StringBuffer();
        for (int i=0; i<hash.length; i++) {
        	String hex = "0"+Integer.toHexString(0xFF & hash[i]);
        	if (hex.length()>2) hex = hex.substring(hex.length()-2);
            rc.append(hex);
        }
        
        return rc.toString();
	}
}

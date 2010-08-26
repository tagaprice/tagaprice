package org.tagaprice.server.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.tagaprice.server.DBConnection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginDAO {

	private DBConnection db;
	
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
		
		if(getId(sid)==null) return false;
		
		
		String sql = "DELETE FROM session " +
				"WHERE " +
				"uid = ? AND sid = ?";
		
		//TODO Problem at deleting the session id :-(
		/*
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, getId(sid));
		pstmt.setString(2, sid);
		
		System.out.println("sql: "+sql+", id: "+getId(sid)+", sid: "+sid);
		
		pstmt.execute();
		
		//int res = pstmt.executeUpdate();
		
		//if(res>0)return true;
		*/
				
		return true;
	}
	
	
	public String getSid(Cookie[] cookies){
			
		if(cookies.length>0	&& cookies[0].getName().equals("TaPSId")){
			return cookies[0].getValue();			
		}		
		return null;
	}
	
	
	public String md5(String in) throws NoSuchAlgorithmException {
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

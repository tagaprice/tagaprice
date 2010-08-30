package org.tagaprice.server.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.http.Cookie;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.exception.InvalidLoginException;

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
			
			//is Sid available and between 24h
			sql = "SELECT uid, sid, last_active FROM session " +
					"WHERE uid = ? " +
					"AND (last_active BETWEEN (NOW() - INTERVAL '1 day') AND NOW())";
			
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, res.getLong("uid"));
			ResultSet res2 = pstmt.executeQuery();
			
			if(res2.next()){
				//An valid Sid is available!
				
				//Update last_active
				sql = "UPDATE session " +
						"SET last_active = NOW() " +
						"WHERE uid = ?";
				pstmt = db.prepareStatement(sql);
				pstmt.setLong(1, res.getLong("uid"));
				
				if(pstmt.executeUpdate()==0)return null;
				
				return res2.getString("sid");
			}else{
				//No Sid, or no Valid Sid is available!
				String session = md5(generateSalt(20));
				
				//UID available?
				sql = "SELECT * FROM session " +
						"WHERE uid = ?";
				pstmt = db.prepareStatement(sql);
				pstmt.setLong(1, res.getLong("uid"));
				ResultSet res3 = pstmt.executeQuery();
				
				if(res3.next()){
					//UID available 
					sql = "UPDATE session " +
							"SET last_active = NOW(), " +
							"sid = ? " +
							"WHERE uid = ?";
					pstmt = db.prepareStatement(sql);
					pstmt.setString(1, session);
					pstmt.setLong(2, res.getLong("uid"));
					
					if(pstmt.executeUpdate()==0)return null;
					
				}else{
					//No Session entry in DB
					sql = "INSERT INTO session " +
					"(uid, sid) " +
					"VALUES " +
					"(?,?)";
			
					pstmt = db.prepareStatement(sql);
					pstmt.setLong(1, res.getLong("uid"));
					pstmt.setString(2, session);
					pstmt.executeUpdate();
				}			
				
				return session;
			}					
			
		}
		
		return null;
	}
	
	public Long getId(String sid) throws IllegalArgumentException, SQLException, InvalidLoginException {
		System.out.println("sid: "+sid);
		
		String sql = "SELECT uid FROM session " +
				"WHERE sid = ? "; 
		
		//TODO Check time slice.
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, sid);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()) {
			throw new InvalidLoginException("No current ID for this SID");
		}

		
		return res.getLong("uid");
	}
	
	public boolean logout(String sid) throws IllegalArgumentException, SQLException, InvalidLoginException {
		
		if(getId(sid)==null) return false;
		
		
		String sql = "" +
				"UPDATE session " +
				"SET last_active = (NOW() - INTERVAL '1 year') " +
				"WHERE sid = ?";
		//Can't ask db for UID!!!		
		
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, sid);
				
		if(pstmt.executeUpdate()==0)return false;
		
				
		return true;
	}
	
	
	public String getSid(Cookie[] cookies) throws InvalidLoginException{
		if(cookies==null) throw new InvalidLoginException("No current ID for this SID");

		if(cookies[0]==null) throw new InvalidLoginException("No current ID for this SID");

		
		if(cookies.length>0	&& cookies[0].getName().equals("TaPSId")){
			return cookies[0].getValue();			
		}	
		throw new InvalidLoginException("No current ID for this SID");
		
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
	
	public String generateSalt(int len) {
		Random rnd = new Random(System.currentTimeMillis());
		String rc = "";

		for (int i = 0; i < len; i++) {
			int n = rnd.nextInt(62);
			char c;
			if (n < 26) c = (char)(n+(int)'a');
			else if (n < 52) c = (char)(n-26+(int)'A');
			else c = (char) (n-52+(int)'0');
			rc += c;
		}
		return rc;
	}
}

package org.tagaprice.server.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.http.Cookie;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.exception.InvalidLoginException;

public class LoginDAO {
	private DBConnection db;
	private static Random rnd = null;
	
	public LoginDAO(DBConnection db) {
		this.db=db;
	}
	
	public String login(String mail, String password) throws SQLException {	
		String sql = "SELECT uid FROM localAccount INNER JOIN account USING(uid) " +
			"WHERE mail = ? AND password = md5(?||salt) AND NOT locked";
		PreparedStatement pstmt = db.prepareStatement(sql);
		pstmt.setString(1, mail);
		pstmt.setString(2, password);
		ResultSet res = pstmt.executeQuery();
		
		if(!res.next()) return null;

		// users are allowed to have several active sessions simultaneously
		String sid = generateSalt(32);
		sql = "INSERT INTO session (uid, sid) VALUES (?, ?)";
		pstmt = db.prepareStatement(sql);
		pstmt.setLong(1, res.getLong("uid"));
		pstmt.setString(2, sid);
		pstmt.executeUpdate();
			
		return sid;
	}
	
	public Long getId(String sid) throws IllegalArgumentException, SQLException, InvalidLoginException {		
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
	
	private static long _getSeed() {
		long rc = 0;

		try {
			FileInputStream in = new FileInputStream("/dev/urandom");
			int n;

			for (int i = 0; i < 8; i++) {
				n = in.read();
				if(n >= 0) {
					rc *= 256;
					rc += n;
				}
			}
		}
		catch (IOException e) { // /dev/urandom can't be read
			/// TODO use log4j
			System.err.println("Warning: using current time as random seed");
			rc = System.currentTimeMillis();
		}

		return rc;
	}

	public static String generateSalt(int len) {
		String rc = "";

		// first time initialization
		if (rnd == null) {
			rnd = new Random(_getSeed());
		}

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

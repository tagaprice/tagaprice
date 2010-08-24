/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: UserHandlerImpl.java
 * Date: 07.07.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocalAccountDAO;
import org.tagaprice.server.dao.LocaleDAO;
import org.tagaprice.server.dao.EntityDAOTest.TestDBConnection;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.LocalAccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;
import org.tagaprice.shared.rpc.LocalAccountHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LocalAccountHandlerImpl extends RemoteServiceServlet implements LocalAccountHandler {
	private DBConnection db;
	private LocalAccountDAO dao;
	private int localeId;
	
	public LocalAccountHandlerImpl() {
		try {
			db = new DBConnection();
			dao = new LocalAccountDAO(db);
			localeId = new LocaleDAO(db).get("English").getId();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLocaleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	@Override
	public boolean checkMailAvailability(String email) throws IllegalArgumentException {		
		try {
			return dao.isEmailEvalable(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isUsernameEvalabel(String username)
			throws IllegalArgumentException {
		
		if(username.length()>=5){
			try {
				return dao.isUsernameEvalabel(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public boolean registerNewUser(String username, String password,
			String confirmPassword, String email, String confirmEmail,
			Address address, boolean gtc)
			throws IllegalArgumentException {
		
		
		//Check Valid
		if(!(isUsernameEvalabel(username) &&
				password.equals(confirmPassword) &&
				checkMailAvailability(email) &&
				email.equals(confirmEmail) &&
				gtc))
			return false;
		
		//Start with saving and sending confirm email
		
		
		
		try {
			
			LocalAccountData account = new LocalAccountData(
					username, 
					localeId, 
					null, 
					email, 
					password, 
					null);

			dao.save(account);
			
			//Test if the password is saved correctly
			String sql = "SELECT password, salt FROM localaccount WHERE uid = ?";
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, account.getId());
			ResultSet res = pstmt.executeQuery();			
			res.next();
			String salt = res.getString("salt");
			String pwdHash = res.getString("password");			
			if(!md5(password+salt).equals(pwdHash)){
				return false;
			}
			
			
			//Send Email
			sql = "SELECT confirm FROM confirmaccount WHERE uid = ?";
			pstmt = db.prepareStatement(sql);
			pstmt.setLong(1, account.getId());
			res = pstmt.executeQuery();
			res.next();
			System.out.println("send Mail: "+res.getString("confirm"));
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RevisionCheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidLocaleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean login(String username, String password)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		
		
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

	@Override
	public boolean confirm(String confirm) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		try {
			return dao.confirm(confirm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return false;
	}

}

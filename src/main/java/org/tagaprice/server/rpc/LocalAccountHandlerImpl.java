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
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocalAccountDAO;
import org.tagaprice.server.dao.LocaleDAO;
import org.tagaprice.server.dao.LoginDAO;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.LocalAccountData;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.exception.RevisionCheckException;
import org.tagaprice.shared.rpc.LocalAccountHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LocalAccountHandlerImpl extends RemoteServiceServlet implements LocalAccountHandler {
	private DBConnection db;
	private LocalAccountDAO dao;
	private LoginDAO loginDao;
	private int localeId;
	
	
	public LocalAccountHandlerImpl() {
		try {
			db = new DBConnection();
			dao = new LocalAccountDAO(db);
			loginDao = new LoginDAO(db);
			localeId = new LocaleDAO(db).get("English").getId();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidLocaleException e) {
			throw new IllegalArgumentException(e);
		} 
		
	}
	
	@Override
	public boolean checkMailAvailability(String email) throws IllegalArgumentException {		
		try {
			return dao.isEmailEvalable(email);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public boolean isUsernameEvalabel(String username)
			throws IllegalArgumentException {
		
		if(username.length()>=5){
			try {
				return dao.isUsernameEvalabel(username);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e);
			} catch (NotFoundException e) {
				throw new IllegalArgumentException(e);
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
			if(!loginDao.md5(password+salt).equals(pwdHash)){
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
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (RevisionCheckException e) {
			throw new IllegalArgumentException(e);
		} catch (InvalidLocaleException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public String login(String username, String password)
			throws IllegalArgumentException {		
		
			try {
				return loginDao.login(username, password);
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException(e);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e);
			}		
		
	}
	
	@Override
	public Long getId() throws IllegalArgumentException, InvalidLoginException {
		
		try {
			return loginDao.getId(getSid());
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public boolean logout() throws IllegalArgumentException, InvalidLoginException {
		
		try {
			return loginDao.logout(getSid());
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		}	
	}
	

	@Override
	public boolean confirm(String confirm) throws IllegalArgumentException {
		try {
			return dao.confirm(confirm);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e);
		} catch (NotFoundException e) {
			throw new IllegalArgumentException(e);
		}	
	}
	
	private String getSid() throws InvalidLoginException{
		System.out.println("getSid 1");
		Cookie[] cookies = this.getThreadLocalRequest().getCookies();
		System.out.println("getSid 2");

		return loginDao.getSid(cookies);
	}

}

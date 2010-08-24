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
import java.sql.SQLException;

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocalAccountDAO;
import org.tagaprice.server.dao.LocaleDAO;
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
	private int localeId;
	LocalAccountDAO lDao;
	String username = "root";
	String password = "tagaprice";
	boolean loggedIn = false;
	
	public LocalAccountHandlerImpl() {
		try {
			db = new DBConnection();
			lDao = new LocalAccountDAO(db);
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
			return lDao.isEmailEvalable(email);
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
				return lDao.isUsernameEvalabel(username);
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
		
		/*
		//Check Valid
		if(!(isUsernameEvalabel(username) &&
				password.equals(confirmPassword) &&
				isEmailEvalable(email) &&
				email.equals(confirmEmail) &&
				gtc))
			return false;
		*/
		//Start with saving and sending confirm email
		
		
		
		try {
			/*
			LocalAccountData account = new LocalAccountData(
					username, 
					localeId, 
					null, 
					email, 
					password, 
					null);*/
			LocalAccountData account = new LocalAccountData("testAccount", localeId, null, "mail@foo.invalid", "my secret password", null);

			lDao.save(account);
			System.out.println("new user");
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
		}
		
		return true;
	}
	
	@Override
	public boolean login(String username, String password)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		//Check in DB if user is available. 
		if(username.equals(this.username) && password.equals(this.password)){
			loggedIn=true;
		}
		
		
		return loggedIn;
	}

}

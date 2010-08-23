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

import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.LocalAccountDAO;
import org.tagaprice.shared.rpc.LocalAccountHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LocalAccountHandlerImpl extends RemoteServiceServlet implements LocalAccountHandler {

	
	LocalAccountDAO lDao;
	String username = "root";
	String password = "tagaprice";
	boolean loggedIn = false;
	
	public LocalAccountHandlerImpl() {
		try {
			lDao = LocalAccountDAO.getInstance(new DBConnection());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isEmailEvalable(String email) throws IllegalArgumentException {
		return lDao.isEmailEvalable(email);
	}

	@Override
	public boolean isUsernameEvalabel(String username)
			throws IllegalArgumentException {
		
		if(username.equals("superuser") || username.length()<5){
			return false;
		}
		
		return true;
	}

	@Override
	public boolean registerNewUser(String username, String password,
			String confirmPassword, String email, String confirmEmail,
			String language, String street, String zip, String county,
			String country, double latitude, double longitude, boolean gtc)
			throws IllegalArgumentException {
		
		//Check Valid
		if(!(isUsernameEvalabel(username) &&
				password.equals(confirmPassword) &&
				isEmailEvalable(email) &&
				email.equals(confirmEmail) &&
				gtc))
			return false;
		
		//Start with saving and sending confirm email
		
		
		
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

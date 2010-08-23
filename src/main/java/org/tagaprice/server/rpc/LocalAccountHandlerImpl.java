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

import org.tagaprice.shared.rpc.LocalAccountHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LocalAccountHandlerImpl extends RemoteServiceServlet implements LocalAccountHandler {

	String username = "root";
	String password = "tagaprice";
	boolean loggedIn = false;
	
	public LocalAccountHandlerImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isEmailEvalable(String email) throws IllegalArgumentException {
		
		if(email.equals("a@a.a")){
			return false;
		}
		return true;
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

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
 * Filename: RegistrationHandler.java
 * Date: 07.07.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.Address;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/account")
public interface LocalAccountHandler extends RemoteService {
	boolean isUsernameEvalabel(String username) throws IllegalArgumentException;	
	boolean checkMailAvailability(String email) throws IllegalArgumentException;
	boolean registerNewUser(
			String username, 
			String password,
			String confirmPassword,
			String email, 
			String confirmEmail,
			Address address,
			boolean gtc) throws IllegalArgumentException;
	
	boolean login(String username, String password) throws IllegalArgumentException;
	
	boolean logout() throws IllegalArgumentException;
	
	boolean confirm(String confirm) throws IllegalArgumentException;
	
}

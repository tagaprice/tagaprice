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

import org.tagaprice.shared.data.Address;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/account")
public interface LocalAccountHandler extends RemoteService {
	boolean checkMailAvailability(String email) throws IllegalArgumentException;
	boolean registerNewUser(
			String username, 
			String password,
			String confirmPassword,
			String email, 
			String confirmEmail,
			Address address,
			boolean gtc) throws IllegalArgumentException;
	
	/**
	 * 
	 * @param mail
	 * @param password
	 * @return SessionID
	 * @throws IllegalArgumentException
	 */
	String login(String mail, String password) throws IllegalArgumentException;
	
	
	Long getId() throws IllegalArgumentException, InvalidLoginException;
	
	boolean logout() throws IllegalArgumentException, InvalidLoginException;
	
	boolean confirm(String confirm) throws IllegalArgumentException;
	
}

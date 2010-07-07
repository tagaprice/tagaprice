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

import org.tagaprice.shared.rpc.UserHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class UserHandlerImpl extends RemoteServiceServlet implements UserHandler {

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
		
		if(username.equals("superuser")){
			return false;
		}
		
		return true;
	}

}

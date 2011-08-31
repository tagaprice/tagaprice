package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;


public class UserAlreadyLoggedInException extends Exception implements IsSerializable {

	private static final long serialVersionUID = 1L;

	public UserAlreadyLoggedInException() {
		super("UserAlreadyLoggedInException");
	}

	public UserAlreadyLoggedInException(String message) {
		super(message);
	}

}

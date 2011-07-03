package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;


public class UserNotLoggedInException extends Exception implements IsSerializable {

	private static final long serialVersionUID = 1L;

	public UserNotLoggedInException() {
		super("UserNotLoggedInException");
	}

	public UserNotLoggedInException(String message) {
		super(message);
	}
	
	public UserNotLoggedInException(String message, Throwable cause) {
		super(message, cause);
	}

}

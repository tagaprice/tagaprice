package org.tagaprice.shared.exceptions;


public class UserNotLoggedInException extends Exception {

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

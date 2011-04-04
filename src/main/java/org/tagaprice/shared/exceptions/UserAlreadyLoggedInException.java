package org.tagaprice.client.gwt.shared.exceptions;


public class UserAlreadyLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyLoggedInException() {
		super("UserAlreadyLoggedInException");
	}

	public UserAlreadyLoggedInException(String message) {
		super(message);
	}

}

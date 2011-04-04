package org.tagaprice.client.gwt.shared.exceptions;


public class UserNotLoggedInException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotLoggedInException() {
		super("UserNotLoggedInException");
	}

	public UserNotLoggedInException(String message) {
		super(message);
	}

}

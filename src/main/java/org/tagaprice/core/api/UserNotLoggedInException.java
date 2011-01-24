package org.tagaprice.core.api;

public class UserNotLoggedInException extends ServerException {

	private static final long serialVersionUID = 1L;

	public UserNotLoggedInException() {
		super("UserNotLoggedInException");
	}

	public UserNotLoggedInException(String message) {
		super(message);
	}

}

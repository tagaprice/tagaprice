package org.tagaprice.core.api;

public class UserAlreadyLoggedInException extends ServerException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyLoggedInException(String message) {
		super(message);
	}

}

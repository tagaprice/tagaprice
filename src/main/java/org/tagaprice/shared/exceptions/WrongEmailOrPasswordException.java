package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Thrown to indicate that a given email does not exist or a given password does not match.
 * @author "forste"
 *
 */
public class WrongEmailOrPasswordException extends Exception implements IsSerializable {


	private static final long serialVersionUID = 1L;

	public WrongEmailOrPasswordException() {
		super("WrongEmailOrPasswordException");
	}

	public WrongEmailOrPasswordException(String message) {
		super(message);
	}
}

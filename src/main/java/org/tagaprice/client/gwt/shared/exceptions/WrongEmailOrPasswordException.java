package org.tagaprice.client.gwt.shared.exceptions;


/**
 * Thrown to indicate that a given email does not exist or a given password does not match.
 * @author "forste"
 *
 */
public class WrongEmailOrPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3709660632429808829L;

	public WrongEmailOrPasswordException() {
		super("WrongEmailOrPasswordException");
	}

	public WrongEmailOrPasswordException(String message) {
		super(message);
	}
}

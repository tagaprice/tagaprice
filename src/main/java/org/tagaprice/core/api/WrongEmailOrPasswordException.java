package org.tagaprice.core.api;


public class WrongEmailOrPasswordException extends ServerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3709660632429808829L;

	public WrongEmailOrPasswordException(String message) {
		super(message);
	}
}

package org.tagaprice.core.api;

/**
 * The base class for all expections which are thrown to indicate that the Server has failed handling the latest request.
 * This may be due to internal errors or by invalid arguments.
 * @author "forste"
 *
 */
public class ServerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3031815250776143829L;

	public ServerException(String message) {
		super(message);
	}

}

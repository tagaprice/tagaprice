/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: RequestException.java
 * Date: 12.06.2010
*/
package org.tagaprice.shared.exception;

public class RequestException extends Exception {
	private static final long serialVersionUID = 1L;

	public RequestException() {
		super();
	}
	
	public RequestException(String message) {
		super(message);
	}
	
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}
}

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
 * Filename: InvalidLocaleException.java
 * Date: 15.06.2010
*/
package org.tagaprice.shared.exception;

public class InvalidLocaleException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidLocaleException() {
		super();
	}

	public InvalidLocaleException(String message) {
		super(message);
	}

	public InvalidLocaleException(Throwable cause) {
		super(cause);
	}

	public InvalidLocaleException(String message, Throwable cause) {
		super(message, cause);
	}
}

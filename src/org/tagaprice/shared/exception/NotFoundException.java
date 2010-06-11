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
 * Filename: NotFoundException.java
 * Date: 11.06.2010
*/
package org.tagaprice.shared.exception;

/*
 * Gets thrown if the client requests a Object (e.g. Product) ID that doesn't
 * exist or to which he doesn't have access privileges
 */
public class NotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}

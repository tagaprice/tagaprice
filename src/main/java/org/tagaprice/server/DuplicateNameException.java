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
 * Filename: DuplicateNameException.java
 * Date: 19.05.2010
*/
package org.tagaprice.server;

public class DuplicateNameException extends Exception {
	private static final long serialVersionUID = 1L;
	public DuplicateNameException() {
		super();
	}
	
	public DuplicateNameException(String msg) {
		super(msg);
	}
	
	public DuplicateNameException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

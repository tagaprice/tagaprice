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
 * Filename: RevisionCheckException.java
 * Date: 14.06.2010
*/
package org.tagaprice.shared.exception;

/**
 * Thrown to indicate that a problem with the revision of an entity has occured. E.g. the current revision might be out dated.
 * @author "forste"
 *
 */
public class RevisionCheckException extends Exception {
	private static final long serialVersionUID = 1L;

	public RevisionCheckException() {
		super();
	}
	
	public RevisionCheckException(String message) {
		super(message);
	}
	
	public RevisionCheckException(String message, Throwable cause) {
		super(message, cause);
	}
}

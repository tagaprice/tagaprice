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
 * Filename: RequestError.java
 * Date: 12.06.2010
*/
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

public class RequestError implements ISerializable {
	private static final long serialVersionUID = 1L;
	private String message;

	public RequestError(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "requestError";
	}

}

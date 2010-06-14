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
 * Filename: Currency.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared;

public class Currency extends Entity {
	private static final long serialVersionUID = 1L;

	public Currency() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Currency(String name, int localeId) {
		super(name, localeId);
	}
	
	public Currency(long id, int rev, String name, int localeId) {
		super(id, rev, name, localeId);
	}

	@Override
	public String getSerializeName() {
		return "currency";
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
}

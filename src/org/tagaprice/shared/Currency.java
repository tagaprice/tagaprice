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

public class Currency implements Entity {
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	
	public Currency(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}

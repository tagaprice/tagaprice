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
 * Filename: Unit.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared;

public class Unit implements Entity {
	private static final long serialVersionUID = 1L;
	
	private long id = 0;
	private String name;
	private long fallbackId = 0;
	private double factor = 0;

	public Unit(long id, String name, long fallbackId, double factor) {
		this.id = id;
		this.name = name;
		this.fallbackId = fallbackId;
		this.factor = factor;
	}
	
	public Unit(long id, String name) {
		this(id, name, 0, 0);
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public long getFallbackId() {
		return fallbackId;
	}
	
	public double getFactor() {
		return factor;
	}
}

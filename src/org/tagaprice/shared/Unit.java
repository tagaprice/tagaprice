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

public class Unit extends Entity {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private long fallbackId = 0;
	private double factor = 0;

	public Unit() {
		super(null);
	}
	
	public Unit(Long id, String name, long fallbackId, double factor) {
		super(id);
		this.name = name;
		this.fallbackId = fallbackId;
		this.factor = factor;
	}
	
	public Unit(long id, String name) {
		this(id, name, 0, 0);
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

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "unit";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof Unit) {
			Unit u = (Unit) o;
			if (getId() != u.getId()) rc = false;
			else if (getFactor() != u.getFactor()) rc = false;
			else if (getName() != null) {
				if (!getName().equals(u.getName())) rc = false;
			}
			else if (u.getName() != null) rc = false;
			
		}
		return rc;
	}
}

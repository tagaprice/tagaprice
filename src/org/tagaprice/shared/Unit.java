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
	
	private long fallbackId = 0;
	private double factor = 0;

	public Unit() {
		super();
	}
	
	public Unit(Long id, int rev, String name, long fallbackId, double factor) {
		super(id, rev, name);
		this.fallbackId = fallbackId;
		this.factor = factor;
	}
	
	public Unit(long id, int rev, String name) {
		this(id, rev, name, 0, 0);
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
		boolean rc = super.equals(o);
		
		if (rc && o instanceof Unit) {
			Unit u = (Unit) o;
			if (getFactor() != u.getFactor()) rc = false;
		}
		return rc;
	}
}

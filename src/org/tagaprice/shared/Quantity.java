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
 * Filename: Quantity.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared;

public class Quantity implements Entity {
	private static final long serialVersionUID = 1L;
	
	private int quantity;
	private Unit unit;
	
	public Quantity(int quantity, Unit unit) {
		setQuantity(quantity);
		setUnit(unit);
	}
	
	public Quantity(int quantity, long unitId, String unitName) {
		this(quantity, new Unit(unitId, unitName));
	}
	
	public Quantity(int quantity) {
		this(quantity, null);
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public void setUnit(long unitId, String unitName) {
		setUnit(new Unit(unitId, unitName));
	}
}

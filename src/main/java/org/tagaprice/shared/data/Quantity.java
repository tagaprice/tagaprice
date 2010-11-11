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
package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

public class Quantity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int quantity;
	private Unit unit;
	
	public Quantity() {
	}
	
	public Quantity(int quantity, Unit unit) {
		setQuantity(quantity);
		setUnit(unit);
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

	@Override
	public String getSerializeName() {
		return "quantity";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof Quantity) {
			Quantity q = (Quantity) o;
			if (getQuantity() != q.getQuantity()) rc = false;
			else if (getUnit() != null) {
				if (!getUnit().equals(q.getUnit())) rc = false;
			}
			else if (q.getUnit() != null) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}

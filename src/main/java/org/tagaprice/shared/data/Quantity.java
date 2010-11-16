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

/**
 * Quantity of a product on a receipt or similar.
 */
public class Quantity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int quantity;
	private Unit unit;
	
	/**
	 * Default constructor needed for serialization
	 */
	public Quantity() { }
	
	/**
	 * Constructor for creating a new {@link Quantity}.
	 * @param quantity number for the quantity of this {@link Quantity}
	 * @param unit {@link Unit} of this {@link Quantity} (may be null)
	 */
	public Quantity(int quantity, Unit unit) {
		setQuantity(quantity);
		setUnit(unit);
	}
	
	/**
	 * Constructor for creating a new {@link Quantity} without {@link Unit}.
	 * @param quantity number for the quantity of this {@link Quantity}
	 */
	public Quantity(int quantity) {
		this(quantity, null);
	}
	
	/**
	 * @return number for the quantity of this {@link Quantity}
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return {@link Unit} of this {@link Quantity}.
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * @param quantity number for the quantity of this {@link Quantity}
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @param unit {@link Unit} of this {@link Quantity}.
	 */
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

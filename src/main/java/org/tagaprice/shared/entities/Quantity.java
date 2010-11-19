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
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

/**
 * TODO find better classname
 * Quantity of a product on a receipt or similar.
 */
public class Quantity implements ISerializable {
	private static final long serialVersionUID = 1L;

	private int _amount;
	private Unit _unit;

	/**
	 * Default constructor needed for serialization
	 */
	public Quantity() {
	}

	/**
	 * Constructor for creating a new {@link Quantity}.
	 * 
	 * @param amount
	 *            number for the quantity of this {@link Quantity}
	 * @param unit
	 *            {@link Unit} of this {@link Quantity} (may be null)
	 */
	public Quantity(int amount, Unit unit) {
		setAmount(amount);
		setUnit(unit);
	}

	/**
	 * Constructor for creating a new {@link Quantity} without {@link Unit}.
	 * 
	 * @param amount
	 *            number for the quantity of this {@link Quantity}
	 */
	public Quantity(int amount) {
		this(amount, null);
	}

	/**
	 * @return number for the quantity of this {@link Quantity}
	 */
	public int getAmount() {
		return _amount;
	}

	/**
	 * @return {@link Unit} of this {@link Quantity}.
	 */
	public Unit getUnit() {
		return _unit;
	}

	/**
	 * @param amount
	 *            number for the quantity of this {@link Quantity}
	 */
	public void setAmount(int amount) {
		this._amount = amount;
	}

	/**
	 * @param unit
	 *            {@link Unit} of this {@link Quantity}.
	 */
	public void setUnit(Unit unit) {
		_unit = unit;
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
			if (getAmount() != q.getAmount())
				rc = false;
			else if (getUnit() != null) {
				if (!getUnit().equals(q.getUnit()))
					rc = false;
			} else if (q.getUnit() != null)
				rc = false;
		} else
			rc = false;

		return rc;
	}
}

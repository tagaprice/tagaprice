package org.tagaprice.shared.entities;

import java.io.Serializable;


/**
 * A {@link Quantity} defines in which way a user can buy a {@link Product}.
 */
public class Quantity implements Serializable {

	private static final long serialVersionUID = 1L;

	private double _quantity;
	private Unit _unit;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Quantity() {
	}

	/**
	 * TODO should quantity be in int?
	 * Create a Quantity object defines by the quantity and a {@link Unit}
	 * 
	 * @param quantity
	 *            the quantity
	 * @param unit
	 */
	public Quantity(double quantity, Unit unit) {
		this._quantity = quantity;
		this._unit = unit;
	}

	/**
	 * Sets the {@link Product} size.
	 * 
	 * @param quantity
	 *            Sets the {@link Product} size.
	 */
	public void setQuantity(double quantity) {
		this._quantity = quantity;
	}

	/**
	 * Returns the size in which a {@link Product} can be
	 * bought
	 * 
	 * @return the size in which a {@link Product} can be
	 *         bought
	 */
	public double getQuantity() {
		return this._quantity;
	}

	/**
	 * Sets the {@link Unit} which a {@link Product} can have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link Product} can have.
	 */
	public void setUnit(Unit unit) {
		this._unit = unit;
	}

	/**
	 * The {@link Unit} in which a {@link Product} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link Product} can be
	 *         bought
	 */
	public Unit getUnit() {
		return this._unit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Quantity [_quantity=" + _quantity + ", _unit=" + _unit + "]";
	}


}

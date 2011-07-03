package org.tagaprice.shared.entities;

import java.math.BigDecimal;

import org.svenson.JSONProperty;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * A {@link Quantity} defines in which way a user can buy a {@link Product}.
 */
public class Quantity implements IsSerializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal _quantity = new BigDecimal("0.0");
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
	public Quantity(BigDecimal quantity, Unit unit) {
		this._quantity = quantity;
		this._unit = unit;
	}

	/**
	 * Sets the {@link Product} size.
	 * 
	 * @param quantity
	 *            Sets the {@link Product} size.
	 */
	public void setQuantity(BigDecimal quantity) {
		this._quantity = quantity;
	}

	/**
	 * Returns the size in which a {@link Product} can be
	 * bought
	 * 
	 * @return the size in which a {@link Product} can be
	 *         bought
	 */
	public BigDecimal getQuantity() {
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
	@JSONProperty(ignore = true)
	public Unit getUnit() {
		return this._unit;
	}

	public String getUnitId(){
		return _unit.getId();
	}

	public void setUnitId(String unitId){
		_unit=new Unit(null, unitId, null, null, null, 1.0);
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

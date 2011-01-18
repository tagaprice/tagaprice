package org.tagaprice.client.gwt.shared.entities.dump;


public class Quantity implements IQuantity {

	private static final long serialVersionUID = -8569323869233802603L;

	private double _quantity;
	private Unit _unit;

	public Quantity() {
		// TODO Auto-generated constructor stub
	}

	public Quantity(double quantity, Unit unit) {
		this._quantity = quantity;
		this._unit = unit;
	}

	@Override
	public void setQuantity(double quantity) {

		this._quantity = quantity;

	}

	@Override
	public double getQuantity() {

		return this._quantity;

	}

	@Override
	public void setUnit(Unit unit) {

		this._unit = unit;

	}

	@Override
	public Unit getUnit() {

		return this._unit;
	}

	@Override
	public IQuantity copy() {
		return new Quantity(this._quantity, this._unit);
	}

	@Override
	public String toString() {
		return String.valueOf(this._quantity) + this._unit.name();
	}

	@Override
	public boolean equals(Object other) {
		if(other instanceof Quantity) {
			return this.equals((Quantity) other);
		} else {
			return false;
		}
	}

	public boolean equals(Quantity other) {
		return this._unit.equals(other._unit) && this._quantity == other._quantity;
	}

}

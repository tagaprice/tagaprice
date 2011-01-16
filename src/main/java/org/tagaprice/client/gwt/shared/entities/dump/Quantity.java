package org.tagaprice.client.gwt.shared.entities.dump;

public class Quantity implements IQuantity {

	private static final long serialVersionUID = -8569323869233802603L;

	private double _quantity;
	private Unit _unit;

	public Quantity() {
		// TODO Auto-generated constructor stub
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

}

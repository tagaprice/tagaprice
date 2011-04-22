package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Quantity;

public interface IQuantityChangeHandler {

	/**
	 * Called if quantity has changed
	 * @param quantity new quantity
	 */
	public void onChange(Quantity quantity);
}

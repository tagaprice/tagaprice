package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

public interface IQuantityChangeHandler {

	/**
	 * Called if quantity has changed
	 * @param quantity new quantity
	 */
	public void onChange(IQuantity quantity);
}

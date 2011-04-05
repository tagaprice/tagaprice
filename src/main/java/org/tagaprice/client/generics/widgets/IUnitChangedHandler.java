package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Unit;

public interface IUnitChangedHandler {

	/**
	 * Returns the new unit.
	 * @param unit the new unit.
	 */
	public void onChange(Unit unit);
}

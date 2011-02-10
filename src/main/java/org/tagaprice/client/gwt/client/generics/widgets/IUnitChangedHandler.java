package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.core.entities.Unit;

public interface IUnitChangedHandler {

	/**
	 * Returns the new unit.
	 * @param unit the new unit.
	 */
	public void onChange(Unit unit);
}

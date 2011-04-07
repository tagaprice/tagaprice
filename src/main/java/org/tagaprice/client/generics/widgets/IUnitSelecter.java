package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Unit;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines primary methods a unit can be selected. There are two different ways to display a unit
 * selector. First you see all units, second you see just possible (related) units. eg. mg, g, kg
 * 
 */
public interface IUnitSelecter extends IsWidget {

	/**
	 * Set the {@link Unit} where all other units must be related to. Is this is not set, all units are displayed
	 * @param unit Defines the {@link Unit} with which all other must be rated to.
	 */
	public void setRelatedUnit(Unit unit);

	/**
	 * Set the {@link Unit}.
	 * @param unit the current {@link Unit}.
	 */
	public void setUnit(Unit unit);

	/**
	 * Returns the selected {@link Unit}.
	 * @return the selected {@link Unit}
	 */
	public Unit getUnit();

	/**
	 * Is called when the unit has changed
	 * @param unitChangedHandler called when the unit has changed
	 */
	void addUnitChangedHandler(IUnitChangedHandler unitChangedHandler);


}

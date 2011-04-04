package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.Unit;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods to select a {@link IQuantity}. This interface must me implemented for
 * every screen.
 * 
 */
public interface IQuantitySelecter extends IsWidget {

	/**
	 * Set the current quantity
	 * @param quantity the current quantity
	 */
	public void setQuantity(IQuantity quantity);

	/**
	 * Returns the current selected quantity
	 * @return the current selected quantity
	 */
	public IQuantity getQuantity();

	/**
	 * If RelatedUnit is set, the user can only select related unit.
	 * @param unit the {@link Unit} with which all other must be rated to.
	 */
	void setRelatedUnit(Unit unit);


	/**
	 * This handler is called when the quantity has changed.
	 * @param quantityChangeHandler is called when the quantity has changed.
	 */
	void addQuantityChangeHandler(IQuantityChangeHandler quantityChangeHandler);


}

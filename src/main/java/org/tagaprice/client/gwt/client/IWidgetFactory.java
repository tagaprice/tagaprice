package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.generics.widgets.ICategorySelecter;

/**
 * This interface must be implemented for every screen!
 *
 */
public interface IWidgetFactory {

	/**
	 * Returns a new instance of the ICategorySelecter for a special screen.
	 * @return a new instance of the ICategorySelecter for a special screen.
	 */
	public ICategorySelecter getNewInstanceOfICategorySelecter();

}

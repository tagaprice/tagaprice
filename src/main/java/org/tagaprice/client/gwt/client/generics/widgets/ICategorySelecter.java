package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;

/**
 * This interface is defines the primary methods to select a {@link ICategory}. This interface must me implemented for
 * every screen.
 * 
 */
public interface ICategorySelecter {

	/**
	 * Set the current category
	 * @param category the current category
	 */
	public void setCategory(ICategory category);
}

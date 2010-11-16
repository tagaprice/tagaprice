package org.tagaprice.client.widgets;

import org.tagaprice.shared.Category;;

/**
 * An ActionListener that will be called if the type change. 
 */
public interface ITypeWidgetHandler {

	/**
	 * Is called if the type change, and returns the new type.
	 * @param type the current type.
	 */
	public void onChange(Category type);
}

package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Widget;

/**
 * Returns the selected widget, and its index
 * 
 */
public interface ISelectiveListHandler {

	/**
	 * Is called if a widget is selected. 
	 * @param widget selected widget
	 * @param index index of the selected widget
	 */
	public void onClick(Widget widget, int index);

}

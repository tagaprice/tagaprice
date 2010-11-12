/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: SelectiveVerticalPanelHandler.java
 * Date: 14.05.2010
 */
package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Widget;

/**
 * Returns the selected widget, and the index
 * 
 */
public interface SelectiveListHandler {
	// TODO Schaun ob es eine schoenere Moeglichkeit gibt

	public void onClick(Widget widget, int index);

}

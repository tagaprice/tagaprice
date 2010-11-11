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
 * Filename: ProgressWidget.java
 * Date: 14.05.2010
*/
package org.tagaprice.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
 
/**
 * Is only a Mockup
 *
 */
public class ProgressWidget extends Composite {
	SimplePanel simplePanel = new SimplePanel();
	Image image;
	int progress;
	
	public ProgressWidget(Image image, int progress) {
		initWidget(simplePanel);
		this.image=image;
		this.progress=progress;
		
		simplePanel.setWidget(this.image);
	}
}

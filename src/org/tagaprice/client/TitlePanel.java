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
 * Filename: TitelPanel.java
 * Date: 15.05.2010
*/
package org.tagaprice.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creates a widget container with standard title 
 * style for the whole project. 
 * 
 */
public class TitlePanel extends Composite {
	VerticalPanel vePa1 = new VerticalPanel();
	SimplePanel siPa = new SimplePanel();
	Label label = new Label();
	
	/**
	 * 
	 * @param title Pagetitle
	 * @param widget
	 */
	public TitlePanel(String title, Widget widget) {
		vePa1.setWidth("100%");
		initWidget(vePa1);
		
		//Style
		setStyleName("TitlePanel");
		
		//Title
		label.setText(title);
		label.setStyleName("TitlePanel-Title");
		vePa1.add(label);
		
		//widget
		siPa.setWidth("100%");
		vePa1.add(siPa);
		siPa.setWidget(widget);
	}
	
	/**
	 * 
	 * @param title
	 * @param widget
	 */
	public void setTitleWidet(String title, Widget widget){
		label.setText(title);
		siPa.setWidget(widget);
	}
}

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
package org.tagaprice.client.widgets;

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
public class TitleWidget extends Composite {
	VerticalPanel vePa1 = new VerticalPanel();
	SimplePanel siPa = new SimplePanel();
	Label label = new Label();
	public enum Level {
		H1, H2, H3
	}
	
	/**
	 * 
	 * @param title Pagetitle
	 * @param widget
	 */
	public TitleWidget(String title, Widget widget, Level level) {
		vePa1.setWidth("100%");
		initWidget(vePa1);
		
		//Style
		setStyleName("TitlePanel");
		
		//Title
		label.setText(title);
		if(level.equals(Level.H1)){
			label.setStyleName("TitlePanel-Title-H1");
		}else if(level.equals(Level.H2)){
			label.setStyleName("TitlePanel-Title-H2");
		}else if(level.equals(Level.H3)){
			label.setStyleName("TitlePanel-Title-H3");
		}
		
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
	public void setTitleWidget(String title, Widget widget){
		label.setText(title);
		siPa.setWidget(widget);
	}
}

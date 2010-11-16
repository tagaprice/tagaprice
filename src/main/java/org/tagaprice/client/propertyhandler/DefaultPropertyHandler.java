/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: NoTypePropertyHandler.java
 * Date: 19.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.shared.data.Property;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DefaultPropertyHandler extends PropertyHandler {
	
	
	VerticalPanel vePa1 = new VerticalPanel();
	TitleWidget title;
	int rowSwap=-1;
	boolean show=false;
	ArrayList<Property> noReadproperties = new ArrayList<Property>();
	
	public DefaultPropertyHandler(HashMap<String, ArrayList<Property>> hashProperties, PropertyChangeHandler handler) {
		super(hashProperties, null, handler);
		
		//Remove Non Used		
		
		vePa1.setWidth("100%");
	
		title = new TitleWidget("Unlisted", vePa1, TitleWidget.Level.H2);
		fillGrid();
		initWidget(title);
	}
	
	private void fillGrid(){
		for(String ks:hashProperties.keySet()){
			for(Property pd:hashProperties.get(ks)){
				if(!pd.getRead())
					vePa1.add(new Label(pd.getTitle()+" | "+pd.getName()+" | "+pd.getValue()));
			}
		}
	}
	
	

}

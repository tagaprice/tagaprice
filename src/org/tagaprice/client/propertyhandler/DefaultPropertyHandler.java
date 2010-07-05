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
import java.util.ListIterator;

import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DefaultPropertyHandler extends PropertyHandler {
	
	
	VerticalPanel vePa1 = new VerticalPanel();
	TitlePanel title;
	int rowSwap=-1;
	boolean show=false;
	ArrayList<PropertyData> noReadproperties = new ArrayList<PropertyData>();
	
	public DefaultPropertyHandler(HashMap<String, ArrayList<PropertyData>> hashProperties, PropertyChangeHandler handler) {
		super(hashProperties, null, handler);
		
		//Remove Non Used		
		
		vePa1.setWidth("100%");
	
		title = new TitlePanel("Unlisted", vePa1, TitlePanel.Level.H2);
		fillGrid();
		initWidget(title);
	}
	
	private void fillGrid(){
		for(String ks:hashProperties.keySet()){
			for(PropertyData pd:hashProperties.get(ks)){
				if(!pd.getRead())
					vePa1.add(new Label(pd.getTitle()+" | "+pd.getName()+" | "+pd.getValue()));
			}
		}
	}
	
	public ArrayList<PropertyData> getPropertyData(){
		return noReadproperties;
		
	}

}

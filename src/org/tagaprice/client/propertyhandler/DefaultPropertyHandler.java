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
import java.util.ListIterator;

import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class DefaultPropertyHandler extends PropertyHandler {
	
	
	Grid grid = new Grid(0, 2);
	TitlePanel title;
	int rowSwap=-1;
	boolean show=false;
	
	public DefaultPropertyHandler(ArrayList<PropertyData> properties, PropertyGroup propGroup) {
		super(properties, propGroup);
		grid.setWidth("100%");
		grid.setStyleName("DefaultPropertyHandler");
	
		title = new TitlePanel(propGroup.getTitle(), grid, TitlePanel.Level.H2);
		fillGrid();
		initWidget(title);
	}
	
	private void fillGrid(){
		ListIterator<PropertyData> iter = properties.listIterator();
		while(iter.hasNext()){
			PropertyData temp = iter.next();
			if(temp.getRead()==false){
				show=true;
				grid.resize(grid.getRowCount()+1, 2);
				grid.getCellFormatter().setWidth(0, 0, "100%");
				grid.getCellFormatter().setStyleName(grid.getRowCount()-1, 0, "DefaultPropertyHandler-Row"+rowSwap);
				grid.getCellFormatter().setStyleName(grid.getRowCount()-1, 1, "DefaultPropertyHandler-Row"+rowSwap);
				grid.setWidget(grid.getRowCount()-1, 0, new Label(temp.getTitle()+ " ("+temp.getUnit().getName()+")"));
				grid.setWidget(grid.getRowCount()-1, 1, new MorphWidget(temp.getValue(),true));
				rowSwap*=-1;
			}
			
		}
		
		title.setVisible(show);
	}

}

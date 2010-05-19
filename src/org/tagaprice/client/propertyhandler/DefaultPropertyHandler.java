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
import java.util.Iterator;

import org.tagaprice.client.MorphWidget;
import org.tagaprice.shared.PropertyData;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class DefaultPropertyHandler extends PropertyHandler {

	Grid grid = new Grid(0, 3);
	ArrayList<PropertyData> properties;
	
	public DefaultPropertyHandler(ArrayList<PropertyData> properties) {
		super(properties);
		this.properties=properties;
		fillGrid();
		
		initWidget(grid);
	}
	
	private void fillGrid(){
		Iterator<PropertyData> iter = properties.iterator();
		while(iter.hasNext()){
			PropertyData temp = iter.next();
			if(temp.getReadCount()==0){
				grid.resize(grid.getRowCount()+1, 3);
				grid.setWidget(grid.getRowCount()-1, 0, new Label(temp.getTitle()));
				grid.setWidget(grid.getRowCount()-1, 1, new MorphWidget(temp.getValue(),true));
				grid.setWidget(grid.getRowCount()-1, 2, new Label(temp.getUnit().getName()));
			}
			
		}
	}

}

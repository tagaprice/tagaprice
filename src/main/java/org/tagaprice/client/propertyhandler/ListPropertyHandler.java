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
 * Filename: ListPropertyHandler.java
 * Date: 25.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.shared.data.Property;
import org.tagaprice.shared.data.PropertyTypeDefinition;
import org.tagaprice.shared.data.PropertyGroup;

import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPropertyHandler extends PropertyHandler {

	HashMap<String, PropertyTypeDefinition> definition = new HashMap<String, PropertyTypeDefinition>();
	TitleWidget title;
	VerticalPanel vePa1 = new VerticalPanel();
	//int rowSwap=-1;
		
	
	public ListPropertyHandler(HashMap<String, ArrayList<Property>> hashProperties,
			PropertyGroup propGroup, 
			PropertyChangeHandler handler) {
		super(hashProperties, propGroup, handler);
		
		vePa1.setWidth("100%");
		
		title = new TitleWidget(propGroup.getTitle(), vePa1, TitleWidget.Level.H2);
		convertToHash();
		
		createGrid();
		initWidget(title);
	}
	
	/**
	 * Converts PropertyDefinition ArrayList to HashMap. So it is easy to find a Type
	 * @param groupElements
	 */
	private void convertToHash(){
		for(PropertyTypeDefinition pg:propGroup.getGroupElements()){
			definition.put(pg.getName(), pg);
		}
	}
	
	
	private void createGrid(){
		
		for(PropertyTypeDefinition pg:propGroup.getGroupElements()){			
			if(hashProperties.get(pg.getName())==null){
				hashProperties.put(pg.getName(), new ArrayList<Property>());
			}
			
			
			ListPropertyItem temp = new ListPropertyItem(
						pg, 
						hashProperties.get(pg.getName()));
			vePa1.add(temp);
			
			temp.addChangeHandler(handler);
			
		}		
	}
	
	
	

}

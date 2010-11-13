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
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPropertyHandler extends APropertyHandler {

	HashMap<String, PropertyDefinition> definition = new HashMap<String, PropertyDefinition>();
	TitleWidget title;
	VerticalPanel vePa1 = new VerticalPanel();
	//int rowSwap=-1;
		
	
	public ListPropertyHandler(HashMap<String, ArrayList<PropertyData>> hashProperties,
			PropertyGroup propGroup, 
			IPropertyChangeHandler handler) {
		super(hashProperties, propGroup, handler);
		
		vePa1.setWidth("100%");
		
		title = new TitleWidget(propGroup.getTitle(), vePa1, TitleWidget.Headline.H2);
		convertToHash();
		
		createGrid();
		initWidget(title);
	}
	
	/**
	 * Converts PropertyDefinition ArrayList to HashMap. So it is easy to find a Type
	 * @param groupElements
	 */
	private void convertToHash(){
		for(PropertyDefinition pg:getPropertyGroup().getGroupElements()){
			definition.put(pg.getName(), pg);
		}
	}
	
	
	private void createGrid(){
		
		for(PropertyDefinition pg:getPropertyGroup().getGroupElements()){			
			if(getPropertyList().get(pg.getName())==null){
				getPropertyList().put(pg.getName(), new ArrayList<PropertyData>());
			}
			
			
			ListPropertyItem temp = new ListPropertyItem(
						pg, 
						getPropertyList().get(pg.getName()));
			vePa1.add(temp);
			
			temp.addChangeHandler(getPropertyChangeHandler());
			
		}		
	}
	
	
	

}

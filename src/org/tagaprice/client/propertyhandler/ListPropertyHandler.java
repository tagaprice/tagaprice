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

import org.tagaprice.client.HorizontalInfoPanel;
import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.MorphWidgetErrorHandler;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPropertyHandler extends PropertyHandler {

	HashMap<String, PropertyDefinition> definition = new HashMap<String, PropertyDefinition>();
	HashMap<String, ArrayList<PropertyData>> usedDefs = new HashMap<String, ArrayList<PropertyData>>();
	TitlePanel title;
	VerticalPanel vePa1 = new VerticalPanel();
	int rowSwap=-1;
	
	public ListPropertyHandler(ArrayList<PropertyData> properties,
			PropertyGroup propGroup) {
		super(properties, propGroup);
		vePa1.setWidth("100%");
		
		title = new TitlePanel(propGroup.getTitle(), vePa1, TitlePanel.Level.H2);
		convertToHash();
		
		createGrid();
		initWidget(title);
	}
	
	/**
	 * Converts PropertyDefinition ArrayList to HashMap. So it is easy to find a Type
	 * @param groupElements
	 */
	private void convertToHash(){
		for(PropertyDefinition pg:propGroup.getGroupElements()){
			definition.put(pg.getName(), pg);
			usedDefs.put(pg.getName(), new ArrayList<PropertyData>());
		}
		
		
		for(PropertyData pd:properties){
			if(usedDefs.get(pd.getName())!=null)
				usedDefs.get(pd.getName()).add(pd);
		}
	}
	
	
	private void createGrid(){
		
		for(PropertyDefinition pg:propGroup.getGroupElements()){
			ListPropertyItem temp = new ListPropertyItem(
					pg, 
					usedDefs.get(pg.getName()));
			vePa1.add(temp);
		}
			
		
	}
	

	
	
	

}

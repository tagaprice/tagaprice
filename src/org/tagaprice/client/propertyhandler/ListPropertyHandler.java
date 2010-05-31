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
import java.util.Iterator;

import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ListPropertyHandler extends PropertyHandler {

	HashMap<String, PropertyDefinition> definition = new HashMap<String, PropertyDefinition>();
	HashMap<String, ArrayList<PropertyData>> usedDefs = new HashMap<String, ArrayList<PropertyData>>();
	TitlePanel title;
	VerticalPanel vePa1 = new VerticalPanel();
	int rowSwap=-1;
		
	
	public ListPropertyHandler(ArrayList<PropertyData> properties,
			PropertyGroup propGroup, 
			PropertyChangeHandler handler) {
		super(properties, propGroup, handler);
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
			
			temp.addChangeHandler(handler);

		}		
	}
	
	@Override
	public ArrayList<PropertyData> getPropertyData(){
		ArrayList<PropertyData> rProperties = new ArrayList<PropertyData>();
		
		
		for(Widget wg:vePa1){
			ArrayList<PropertyData> pdList = ((ListPropertyItem)(wg)).getPropertyData();
			for(PropertyData pd:pdList){
				rProperties.add(pd);
			}
		}		
		
		return rProperties;
	}
	
	
	

}

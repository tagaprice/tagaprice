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

import org.tagaprice.client.HorizontalInfoPanel;
import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.MorphWidgetErrorHandler;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPropertyHandler extends PropertyHandler {

	HashMap<String, PropertyDefinition> definition = new HashMap<String, PropertyDefinition>();
	HashMap<String, ArrayList<PropertyData>> usedDefs = new HashMap<String, ArrayList<PropertyData>>();
	TitlePanel title;
	Grid grid = new Grid(0, 3);
	VerticalPanel vePa1 = new VerticalPanel();
	int rowSwap=-1;
	
	public ListPropertyHandler(ArrayList<PropertyData> properties,
			PropertyGroup propGroup) {
		super(properties, propGroup);
		grid.setWidth("100%");
		vePa1.setWidth("100%");
		grid.setStyleName("DefaultPropertyHandler");
		
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
			if(!usedDefs.get(pg.getName()).isEmpty()){
				for(PropertyData pd:usedDefs.get(pg.getName())){
					addToGrid(
							definition.get(pg.getName()).getTitle(), 
							pd.getValue(),
							definition.get(pg.getName()).getType(),
							definition.get(pg.getName()).getUnit().getName());
				}
			}			
			
			if(!pg.isUnique() || usedDefs.get(pg.getName()).isEmpty()){
				addToGrid(
						definition.get(pg.getName()).getTitle(), 
						"", 
						definition.get(pg.getName()).getType(),
						definition.get(pg.getName()).getUnit().getName());
			}
		}
			
		
	}
	
	private void addToGrid(String title, String value, Datatype type, String unit){
		final HorizontalInfoPanel temp = new HorizontalInfoPanel();
		MorphWidget mp = new MorphWidget(value,type, true);
		mp.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			
			@Override
			public void onError(Datatype errorType) {
				temp.showInfo("Error");
				
			}

			@Override
			public void onSuccess(Datatype errorType) {
				temp.showInfo(false);
				
			}
		});
		Label lTitle = new Label(title);
		temp.add(lTitle);
		temp.getPanel().setCellWidth(lTitle, "100%");
		temp.add(mp);
		temp.add(new Label(unit));
		
		temp.setStyleName("DefaultPropertyHandler-Row"+rowSwap);
		rowSwap*=-1;

		
		vePa1.add(temp);
	}
	
	
	

}

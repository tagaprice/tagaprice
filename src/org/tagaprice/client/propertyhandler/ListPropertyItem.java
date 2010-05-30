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
 * Filename: ListPropertyItem.java
 * Date: 30.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;

import org.tagaprice.client.HorizontalInfoPanel;
import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.MorphWidgetErrorHandler;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyDefinition.Datatype;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ListPropertyItem extends Composite {
	VerticalPanel vePa1 = new VerticalPanel();
	PropertyDefinition definition;
	ArrayList<PropertyData> propertyData;
	
	
	public ListPropertyItem(
			PropertyDefinition definition, 
			ArrayList<PropertyData> propertyData) {
		this.definition=definition;
		this.propertyData=propertyData;
		
		
		//style
		vePa1.setWidth("100%");
		
		
		initWidget(vePa1);
		
		fillItems();
	}
	
	private void fillItems(){		
		
		
		for(PropertyData pd:propertyData){
			addItem(pd);
		}
		
		if(propertyData.isEmpty() || !definition.isUnique()){
			addItem(new PropertyData(
					definition.getName(), 
					definition.getTitle(), 
					"", 
					definition.getUnit()));
		}
		
	}
	
	private void addItem(final PropertyData pd){
		final HorizontalInfoPanel temp = new HorizontalInfoPanel();
		final MorphWidget mp = new MorphWidget(pd.getValue(),definition.getType(), true);
		pd.setName(""+Math.random()); //Copy Error
		
		//Listen
		mp.addMorphWidgetErrorHandler(new MorphWidgetErrorHandler() {
			
			@Override
			public void onSuccess(Datatype errorType) {
				System.out.println("success");
				
				pd.setValue(mp.getText());
				
				
				if(propertyData.contains(pd)){
					System.out.println("is Including: "+pd.getValue());
				}else{
					System.out.println("is not Including: "+pd.getValue());
				}
				
				if(!definition.isUnique() && !propertyData.contains(pd)){
					propertyData.add(pd);
					addItem(new PropertyData(
							definition.getName(), 
							definition.getTitle(), 
							"", 
							definition.getUnit()));
				}
				
				temp.showInfo(false);
				
			}
			
			@Override
			public void onError(Datatype errorType) {
				temp.showInfo("Error");
			}
			
			@Override
			public void onEmpty() {
				System.out.println("on Empty");
				propertyData.remove(pd);
				if(!definition.isUnique()){
					vePa1.remove(temp);
				}
				temp.showInfo(false);
			}
		});
		
		Label lTitle = new Label(definition.getTitle());
		temp.add(lTitle);
		temp.getPanel().setCellWidth(lTitle, "100%");
		temp.add(mp);
		temp.add(new Label(definition.getUnit().getName()));
		vePa1.add(temp);
	}
}

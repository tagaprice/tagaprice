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
 * Filename: NutrionFactsPropertyHandler.java
 * Date: 20.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.ListIterator;

import org.tagaprice.client.MorphWidget;
import org.tagaprice.client.TitlePanel;
import org.tagaprice.shared.PropertyData;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;

public class NutritionFactsPropertyHandler extends PropertyHandler {

	Grid grid = new Grid(6, 2);
	ArrayList<PropertyData> properties;
	TitlePanel title = new TitlePanel("Nutrition Facts", grid, TitlePanel.Level.H2);
	public NutritionFactsPropertyHandler(ArrayList<PropertyData> properties) {
		super(properties);
		this.properties=properties;
		grid.setWidth("100%");
		grid.setStyleName("NutritionFactsPropertyHandler");
		grid.getCellFormatter().setWidth(0, 0, "100%");
		fillGrid();
		
		initWidget(title);
	}

	private void fillGrid(){
		grid.setWidget(0, 0, new Label("Brennwert (kj)"));
		grid.setWidget(1, 0, new Label("Eiweiss (g)"));
		grid.setWidget(2, 0, new Label("Kohlenhydrate (g)"));
		grid.setWidget(3, 0, new Label("Fett (g)"));
		grid.setWidget(4, 0, new Label("Ballaststoffe (g)"));
		grid.setWidget(5, 0, new Label("Natrium (g)"));
		
		
		//Style
		grid.getCellFormatter().setStyleName(0, 0, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(0, 1, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(1, 0, "DefaultPropertyHandler-Row1");
		grid.getCellFormatter().setStyleName(1, 1, "DefaultPropertyHandler-Row1");
		grid.getCellFormatter().setStyleName(2, 0, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(2, 1, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(3, 0, "DefaultPropertyHandler-Row1");
		grid.getCellFormatter().setStyleName(3, 1, "DefaultPropertyHandler-Row1");
		grid.getCellFormatter().setStyleName(4, 0, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(4, 1, "DefaultPropertyHandler-Row-1");
		grid.getCellFormatter().setStyleName(5, 0, "DefaultPropertyHandler-Row1");
		grid.getCellFormatter().setStyleName(5, 1, "DefaultPropertyHandler-Row1");
		
		//FindProperties
		ListIterator<PropertyData> iter = properties.listIterator();
		while(iter.hasNext()){
			PropertyData temp = iter.next();
			if(temp.getName().equals("energy")){
				grid.setWidget(0, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}else if(temp.getName().equals("protein")){
				grid.setWidget(1, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}else if(temp.getName().equals("carbohydrate")){
				grid.setWidget(2, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}else if(temp.getName().equals("fat")){
				grid.setWidget(3, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}else if(temp.getName().equals("fiber")){
				grid.setWidget(4, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}else if(temp.getName().equals("sodium")){
				grid.setWidget(5, 1, new MorphWidget(temp.getValue(), true));
				temp.incReadCount();
			}
			
		}
	}
}
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
 * Filename: PropertyHandler.java
 * Date: 19.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;

import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;

import com.google.gwt.user.client.ui.Composite;

abstract class PropertyHandler extends Composite implements IPropertyHandler {

	protected ArrayList<PropertyData> properties;
	protected PropertyGroup propGroup;
	PropertyChangeHandler handler;
	
	public PropertyHandler(ArrayList<PropertyData> properties, PropertyGroup propGroup, PropertyChangeHandler handler) {
		this.properties=properties;
		this.propGroup = propGroup;
		this.handler=handler;
	}
	
	
	public void addChangeHandler(PropertyChangeHandler handler){
		this.handler=handler;
	}
	
	public ArrayList<PropertyData> getPropertyData(){
		return properties;
		
	}
	
}

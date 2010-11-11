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
import java.util.HashMap;

import org.tagaprice.shared.data.PropertyData;
import org.tagaprice.shared.data.PropertyGroup;

import com.google.gwt.user.client.ui.Composite;

abstract class PropertyHandler extends Composite implements IPropertyHandler {

	protected HashMap<String, ArrayList<PropertyData>> hashProperties;
	protected PropertyGroup propGroup;
	PropertyChangeHandler handler;
	
	public PropertyHandler(HashMap<String, ArrayList<PropertyData>> hashProperties, PropertyGroup propGroup, PropertyChangeHandler handler) {
		this.hashProperties=hashProperties;
		this.propGroup = propGroup;
		this.handler=handler;
	}
	
	
	public void addChangeHandler(PropertyChangeHandler handler){
		this.handler=handler;
	}

}

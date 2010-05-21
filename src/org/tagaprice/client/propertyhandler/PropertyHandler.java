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

import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;

import com.google.gwt.user.client.ui.Composite;

abstract class PropertyHandler extends Composite {

	protected ArrayList<PropertyData> properties;
	protected PropertyGroup propGroup;
	
	public PropertyHandler(ArrayList<PropertyData> properties, PropertyGroup propGroup) {
		this.properties=properties;
		this.propGroup = propGroup;
	}
	
}

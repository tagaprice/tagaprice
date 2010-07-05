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
 * Filename: IPropertyHandler.java
 * Date: 31.05.2010
*/
package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.shared.PropertyData;

public interface IPropertyHandler {
	public HashMap<String, ArrayList<PropertyData>> getHashProperties();
}

/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: tagaprice
 * Filename: PropertyList.java
 * Date: 20.05.2010
 */
package org.tagaprice.shared;

import java.util.ArrayList;

public class PropertyList extends ArrayList<PropertyData> implements Serializable {
	private static final long serialVersionUID = 1L;

	public void add(PropertyList p){
		for(PropertyData pd: p){
			this.add(pd);
		}
	}

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "propertyList";
	}

}

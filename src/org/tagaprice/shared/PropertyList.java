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
import java.util.Iterator;

public class PropertyList extends ArrayList<PropertyData> implements Entity {
	private static final long serialVersionUID = 1L;

	public PropertyList() {
	}

	@Override
	public String getSerializeName() {
		return "propertyList";
	}
	
	public boolean euqals(Object o) {
		boolean rc = true;
		
		if (o instanceof PropertyList) {
			PropertyList l = (PropertyList) o;
			Iterator<PropertyData> it1 = iterator(), it2 = l.iterator();
			
			while (rc && it1.hasNext() && it2.hasNext()) {
				rc = it1.next().equals(it2.next());
			}
		}
		else rc = false;
		
		return rc;
	}

}

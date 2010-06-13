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

public class SearchResult<T extends Serializable> extends ArrayList<T> implements Entity {
	private static final long serialVersionUID = 1L;

	public SearchResult() {
	}

	@Override
	public String getSerializeName() {
		return "searchResult";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof SearchResult<?>) {
			SearchResult<?> l = (SearchResult<?>) o;
			Iterator<?> it1 = iterator(), it2 = l.iterator();
			
			while (rc && it1.hasNext() && it2.hasNext()) {
				Object a = it1.next(), b = it2.next();
				rc = a.equals(b);
			}
		}
		else rc = false;
		

		return rc;
	}
}
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

/**
 * Represents a Search result as a list.
 * 
 * @param <T> Generic parameter for this SearchResult, must extend {@link Serializable}.
 */
public class SearchResult<T extends Serializable> extends ArrayList<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Just creates an empty {@link ArrayList}.
	 */
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
			if (it1.hasNext() || it2.hasNext()) rc = false;
		}
		else rc = false;
		

		return rc;
	}
	
	public String toString() {
		String rc = "SearchResult(count="+size()+") {\n";
		Iterator<T> it = iterator();
		while(it.hasNext()) {
			rc += it.next().toString()+"\n";
		}
		rc += "}";
		return rc;
	}
}

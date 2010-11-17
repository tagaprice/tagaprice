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
 * An ArrayList that can only contain objects extending {@link Serializable}
 * and is itself extending {@link Serializable}. 
 * 
 * TODO refactor this class (build wrapper instead ?)
 */
public class SerializableArrayList<T extends Serializable> extends ArrayList<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Just creates an empty {@link ArrayList}.
	 */
	public SerializableArrayList() {
	}

	@Override
	public String getSerializeName() {
		return "searchResult"; //TODO change this ?
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof SerializableArrayList<?>) {
			SerializableArrayList<?> l = (SerializableArrayList<?>) o;
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
	
	@Override
	public String toString() {
		String rc = "SearchResult(count="+size()+") {\n"; //TODO change this?
		Iterator<T> it = iterator();
		while(it.hasNext()) {
			rc += it.next().toString()+"\n";
		}
		rc += "}";
		return rc;
	}
}

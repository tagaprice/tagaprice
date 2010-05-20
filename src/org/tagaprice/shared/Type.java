/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: ProductType.java
 * Date: May 20, 2010
*/
package org.tagaprice.shared;

public class Type {

	private long id;
	private String title;
	private Type superType;
	private PropertyList properties;
	
	public Type(String _title, Type _superType){
		title=_title;
		superType = _superType;
		properties.add(superType.getProperties());
		
	}
	
	public PropertyList getProperties(){
		return properties;
	}
	
	
	
}

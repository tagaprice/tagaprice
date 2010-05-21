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

import java.util.ArrayList;

public class Type {

	private long id;
	private String title;
	private Type superType;
	private ArrayList<PropertyGroup> properties;
	
	public Type(String _title, Type _superType){
		title=_title;
		superType = _superType;
		properties.addAll(superType.getPropertyGroups());
		properties = new ArrayList<PropertyGroup>();
		
	}
	
	//type is a main category
	public Type(String _title){
		title=_title;
		superType = this;
		properties = new ArrayList<PropertyGroup>();
	}
	
	public ArrayList<PropertyGroup> getPropertyGroups(){
		return properties;
	}
	
	public void addPropertyGroups(ArrayList<PropertyGroup> properties){
		for(PropertyGroup p: properties)
		properties.add(p);
	}
	
	public void addPropertyGroup(PropertyGroup property){
		properties.add(property);
	}	
	
	public long getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	
}

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

public class Type extends Entity {
	private static final long serialVersionUID = 1L;

	private Type superType;
	private ArrayList<PropertyGroup> properties = new ArrayList<PropertyGroup>();
	
	public Type() {
		super();
	}
	
	/**
	 * 
	 * @param title
	 * @param localeId Identifies the type by the Location
 	 * @param _superType
	 */
	public Type(String title, int localeId, Type _superType){
		super(title, localeId); 
		superType = _superType;

		if (superType != null) {
			properties.addAll(superType.getPropertyGroups());
		}
	}
	
	//type is a main category
	public Type(String title, int localeId) {
		this(title, localeId, null);
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
	
	public Type getSuperType(){
		return superType;
	}

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "unit";
	}
	
	// TODO implement missing Type.equals()
}

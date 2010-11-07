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
	
	/**
	 * default constructor (needed for serialization)
	 */
	public Type() {
		super();
	}
	
	/**
	 * constructor for querying a Type (using TypeDAO)
	 * @param id Type ID
	 */
	public Type(long id) {
		super(id);
	}
	
	/**
	 * constructor for querying a specific Type revision (using TypeDAO) 
	 * @param id Type ID
	 * @param rev Type revision
	 */
	public Type(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor for saving a new Type (using TypeDAO)
	 * @param title descriptive Type name
	 * @param localeId Type's locale
	 * @param creatorId Type's creator (usually the currently logged in User)
	 * @param superType reference to a supertype (may be null)
	 */
	public Type(String title, int localeId, long creatorId, Type superType) {
		super(title, localeId, creatorId);
		this.superType = superType;
	}
	
	/**
	 * constructor for saving changes of an existing Type (using TypeDAO)
	 * @param typeId Type ID
	 * @param rev Type revision (will be checked by TypeDAO to prevent concurrent write attempts)
	 * @param title (new) descriptive Type name
	 * @param creatorId Type revision's creator (usually the currently logged in User)
	 * @param superType (new) reference to a supertype (may be null)
	 */
	public Type(long typeId, int rev, String title, long creatorId, Type superType) {
		super(typeId, rev, title, creatorId);
		this.superType = superType;
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
	
	public void setSuperType(Type superType){
		this.superType=superType;
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

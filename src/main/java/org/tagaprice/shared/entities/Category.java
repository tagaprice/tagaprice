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
package org.tagaprice.shared.entities;

import java.util.ArrayList;


/**
 * Categories are used to display and format {@link PropertyGroup}s on {@link Product}- and {@link Shop} pages.
 */
public class Category extends Entity {
	private static final long serialVersionUID = 1L;

	private Category superCategory;
	private ArrayList<PropertyGroup> propertyGroups = new ArrayList<PropertyGroup>();
	
	/**
	 * default constructor (needed for serialization)
	 */
	public Category() {
		super();
	}
	
	/**
	 * Constructor for querying a {@link Category}.
	 * @param id {@link Category} ID
	 */
	public Category(long id) {
		super(id);
	}
	
	/**
	 * Constructor for querying a specific {@link Category} revision. 
	 * @param id {@link Category} ID
	 * @param rev {@link Category} revision
	 */
	public Category(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * Constructor for saving a new {@link Category}.
	 * @param title descriptive {@link Category} name
	 * @param localeId {@link Category}'s locale
	 * @param creatorId {@link Category}'s creator (usually the currently logged in User)
	 * @param superCategory reference to a superCategory (may be null)
	 */
	public Category(String title, int localeId, long creatorId, Category superCategory) {
		super(title, localeId, creatorId);
		this.superCategory = superCategory;
	}
	
	/**
	 * Constructor for saving changes of an existing {@link Category}.
	 * @param id {@link Category} ID
	 * @param rev {@link Category} revision (will be checked by TypeDAO to prevent concurrent write attempts)
	 * @param title (new) descriptive {@link Category} name
	 * @param creatorId {@link Category}'s creator (usually the currently logged in User)
	 * @param superCategory (new) reference to a superCategory (may be null)
	 */
	public Category(long id, int rev, String title, long creatorId, Category superType) {
		super(id, rev, title, creatorId);
		this.superCategory = superType;
	}
	
	/**
	 * @return all {@link PropertyGroup}s of this {@link Category}.
	 */
	public ArrayList<PropertyGroup> getPropertyGroups(){
		return propertyGroups;
	}
	
	/**
	 * @param propertyGroups add all of this {@link PropertyGroup}s.
	 */
	public void addPropertyGroups(ArrayList<PropertyGroup> propertyGroups){
		for(PropertyGroup p: propertyGroups)
		propertyGroups.add(p);
	}
	
	/**
	 * @param propertyGroup add this {@link PropertyGroup}.
	 */
	public void addPropertyGroup(PropertyGroup propertyGroup){
		propertyGroups.add(propertyGroup);
	}
	
	/**
	 * @return the superCategory (may be null).
	 */
	public Category getSuperCategory(){
		return superCategory;
	}
	
	/**
	 * @param superCategory the superCategory of this Category (may be null).
	 */
	public void setSuperCategory(Category superCategory){
		this.superCategory = superCategory;
	}

	@Override
	public String getSerializeName() {
		return "category";
	}
	
	// TODO implement missing Type.equals()
}

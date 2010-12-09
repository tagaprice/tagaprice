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
 * A {@link Category} builds a hierarchy of labels, used for {@link Shop}s and {@link Product}s. It also holds
 * {@link PropertyGroup}s to format groups of properties and present default-properties for the user to enter
 * 
 */
public class Category extends Entity {
	private static final long serialVersionUID = 1L;

	private Category _superCategory;
	private ArrayList<PropertyGroup> _propertyGroups = new ArrayList<PropertyGroup>();

	/**
	 * default constructor (needed for serialization)
	 */
	public Category() {
		super();
	}

	/**
	 * Constructor for querying a {@link Category}.
	 * 
	 * @param id
	 *            {@link Category} ID in the database
	 */
	public Category(long id) {
		super(id);
	}

	/**
	 * Constructor for querying a specific {@link Category} revision.
	 * 
	 * @param id
	 *            {@link Category} ID in the database
	 * @param rev
	 *            {@link Category} revision
	 */
	public Category(long id, int rev) {
		super(id, rev);
	}

	/**
	 * Constructor for saving a new {@link Category}.
	 * 
	 * @param title
	 *            descriptive {@link Category} name
	 * @param localeId
	 *            {@link Category}'s locale
	 * @param creatorId
	 *            {@link Category}'s creator (usually the currently logged in User)
	 * @param superCategory
	 *            reference to a super{@link Category} (may be null)
	 */
	public Category(String title, int localeId, long creatorId, Category superType) {
		super(title, localeId, creatorId);
		this._superCategory = superType;
	}

	/**
	 * Constructor for saving changes of an existing {@link Category}.
	 * 
	 * @param categoryId
	 *            {@link Category} ID in the database
	 * @param rev
	 *            {@link Category} revision this revision is based on
	 * @param title
	 *            descriptive {@link Category} name
	 * @param localeId
	 *            {@link Category}'s locale
	 * @param creatorId
	 *            {@link Category}'s creator (usually the currently logged in User)
	 * @param superCategory
	 *            reference to a super{@link Category} (may be null)
	 */
	public Category(long categoryId, int rev, String title, long creatorId, Category superType) {
		super(categoryId, rev, title, creatorId);
		this._superCategory = superType;
	}

	/**
	 * @return the {@link PropertyGroup}s of this {@link Category}.
	 */
	public ArrayList<PropertyGroup> getPropertyGroups() {
		return _propertyGroups;
	}

	/**
	 * @param propertyGroups
	 *            add all this {@link PropertyGroup}s
	 */
	public void addPropertyGroups(ArrayList<PropertyGroup> propertyGroups) {
		propertyGroups.addAll(propertyGroups);
	}

	/**
	 * @param property
	 *            add this {@link PropertyGroup}
	 */
	public void addPropertyGroup(PropertyGroup property) {
		_propertyGroups.add(property);
	}

	/**
	 * @return the {@link Category} this {@link Category} is a sub{@link Category} of
	 */
	public Category getSuperCategory() {
		return _superCategory;
	}

	/**
	 * @param superCategory
	 *            set a new super{@link Category}
	 */
	public void setSuperCategory(Category superCategory) {
		this._superCategory = superCategory;
	}

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "unit";
	}

	/**
	 * TODO implement this, or leave it up to {@link Entity}?
	 */
	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO implement missing Category.equals()
}

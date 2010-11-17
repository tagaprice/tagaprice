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
 * Filename: PropertyGroup.java
 * Date: May 21, 2010
*/
package org.tagaprice.shared.entities;

import java.util.ArrayList;

import org.tagaprice.shared.ISerializable;

/**
 * A {@link PropertyGroup} contains the definitions of the group members 
 * used in the Page-Widgets for handler  
 * e.g. NutritionFacts is a group consisting of the {@link PropertyTypeDefinition} for the Property "sugar", "fat", etc
 **/
public class PropertyGroup implements ISerializable {
	
	/** TODO move to own file, maybe refactor so this is not needed anymore (more dynamic allocation of {@link PropertyGroup}s... */
	public enum GroupType implements ISerializable {
		LIST, NUTRITIONFACTS;

		public String getSerializeName() {
			return "propertyGroupType";
		}
	}

	private static final long serialVersionUID = 1L;
	private String title;
	private GroupType type;
	private ArrayList<PropertyTypeDefinition>  groupElements;
	
	/**
	 * Default constructor needed for serialization.
	 */
	public PropertyGroup() { }
	
	/**
	 * Create a new {@link PropertyGroup}
	 * @param title title of this {@link PropertyGroup}
	 * @param type {@link GroupType} of this {@link PropertyGroup}
	 * @param groupElements list of {@link PropertyTypeDefinition}s in this {@link PropertyGroup}
	 */
	public PropertyGroup(String title, GroupType type, ArrayList<PropertyTypeDefinition> groupElements) {
		this(title, type);
		this.groupElements = groupElements;
	}
	
	/**
	 * Create a new {@link PropertyGroup} without any {@link PropertyTypeDefinition} elements
	 * @param title title of this {@link PropertyGroup}
	 * @param type {@link GroupType} of this {@link PropertyGroup}
	 */
	public PropertyGroup(String title, GroupType type) {
		this.type=type;
		this.title=title;
		groupElements = new ArrayList<PropertyTypeDefinition>();
	}
	
	/**
	 * @param propertyTypeDefinition add this {@link PropertyTypeDefinition}
	 * to the elements of this {@link PropertyGroup}
	 */
	public void addGroupElement(PropertyTypeDefinition propertyTypeDefinition) {
		groupElements.add(propertyTypeDefinition);
	}
	
	/**
	 * @param propertyTypeDefinition add all {@link PropertyTypeDefinition}s
	 * to the elements of this {@link PropertyGroup}
	 */
	public void addGroupElements(ArrayList<PropertyTypeDefinition> propertyTypeDefinition) {
		groupElements.addAll(propertyTypeDefinition);
	}
	
	/**
	 * @return all {@link PropertyTypeDefinition} elements of this {@link PropertyGroup}.
	 */
	public ArrayList<PropertyTypeDefinition> getGroupElements() {
		return groupElements;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the type
	 */
	public GroupType getType() {
		return type;
	}

	
	@Override
	public String getSerializeName() {
		return "PropertyGroup";
	}
}

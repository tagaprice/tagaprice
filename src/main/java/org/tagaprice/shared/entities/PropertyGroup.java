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
 * A property group contains the definitions of the group members 
 * used in the Page-Widgets for handler  
 * e.g. NutritionFacts is a group consisting of the PropertyDefinition for the Property "sugar", "fat", etc
 * 
 * **/
public class PropertyGroup implements ISerializable {
	public enum GroupType implements ISerializable {
		LIST, NUTRITIONFACTS;

		public String getSerializeName() {
			return "propertyGroupType";
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private GroupType type;
	private ArrayList<PropertyTypeDefinition>  groupElements;
	
	public PropertyGroup() {
		// TODO Auto-generated constructor stub
	}
	
	public PropertyGroup(String title, GroupType type, ArrayList<PropertyTypeDefinition>  groupElements){
		this(title, type);
		this.groupElements = groupElements;
	}
	
	public PropertyGroup(String title, GroupType type){
		this.type=type;
		this.title=title;
		groupElements = new ArrayList<PropertyTypeDefinition>();
	}
	
	public void addGroupElement(PropertyTypeDefinition propDef){
		groupElements.add(propDef);
	}
	
	public void addGroupElements(ArrayList<PropertyTypeDefinition> propDefs){
		groupElements.addAll(propDefs);
	}
	
	public ArrayList<PropertyTypeDefinition> getGroupElements(){
		return groupElements;
	}

	public String getTitle() {
		return title;
	}

	public GroupType getType() {
		return type;
	}

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "PropertyGroup";
	}
	
	
}

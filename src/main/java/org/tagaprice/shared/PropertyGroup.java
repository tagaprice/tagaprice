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
package org.tagaprice.shared;

import java.util.ArrayList;

/**
 * A property group contains the definitions of the group members 
 * used in the Page-Widgets for handler  
 * e.g. NutritionFacts is a group consisting of the PropertyDefinition for the Property "sugar", "fat", etc
 * 
 * **/
public class PropertyGroup implements Serializable {
	public enum GroupType implements Serializable {
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
	private ArrayList<PropertyDefinition>  groupElements;
	
	public PropertyGroup() {
		// TODO Auto-generated constructor stub
	}
	
	public PropertyGroup(String title, GroupType type, ArrayList<PropertyDefinition>  groupElements){
		this(title, type);
		this.groupElements = groupElements;
	}
	
	public PropertyGroup(String title, GroupType type){
		this.type=type;
		this.title=title;
		groupElements = new ArrayList<PropertyDefinition>();
	}
	
	public void addGroupElement(PropertyDefinition propDef){
		groupElements.add(propDef);
	}
	
	public void addGroupElements(ArrayList<PropertyDefinition> propDefs){
		groupElements.addAll(propDefs);
	}
	
	public ArrayList<PropertyDefinition> getGroupElements(){
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

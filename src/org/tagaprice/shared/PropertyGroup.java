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

public class PropertyGroup {

	private ArrayList<PropertyDefinition>  groupElements;
	
	public PropertyGroup(ArrayList<PropertyDefinition>  groupElements){
		this.groupElements = groupElements;
	}
	
	public PropertyGroup(){
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
	
}

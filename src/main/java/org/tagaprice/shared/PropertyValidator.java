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
 * Filename: PropertyValidator.java
 * Date: 05.07.2010
*/
package org.tagaprice.shared;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.shared.PropertyDefinition.Datatype;

public class PropertyValidator {

	
	public static boolean isValid(Category type, SearchResult<PropertyData> properties){
		
		HashMap<String, ArrayList<PropertyData>> pl = propertyListToHash(properties);
		PropertyGroup pg = extractPropertyGroupFromType(type);
		
		//TestCount
		if(!testCount(pg,pl))
			return false;
		
		//TestInt
		if(!testInt(pg, pl))
			return false;
		
		//TestDouble
		if(!testDouble(pg, pl))
			return false;
		
		return true;
	}
	
	private static boolean testCount(PropertyGroup pg, HashMap<String, ArrayList<PropertyData>> pl){
		
		for(PropertyDefinition pd:pg.getGroupElements()){
			if(pl.get(pd.getName())!=null 
					&& pd.isUnique()
					&& pl.get(pd.getName()).size()>1){
				return false;
			}
			
		}
		
		return true;
	}
	
	
	private static boolean testInt(PropertyGroup pg,HashMap<String, ArrayList<PropertyData>> pl){
		
		for(PropertyDefinition pd:pg.getGroupElements()){
			if(pd.getType().equals(Datatype.INT) && pl.get(pd.getName())!=null){
				for(PropertyData pDa:pl.get(pd.getName())){		
					try{
						Integer.parseInt(pDa.getValue());
					} 
					catch(NumberFormatException nfe) {	
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	private static boolean testDouble(PropertyGroup pg,HashMap<String, ArrayList<PropertyData>> pl){
		
		for(PropertyDefinition pd:pg.getGroupElements()){
			if(pd.getType().equals(Datatype.DOUBLE) && pl.get(pd.getName())!=null){
				for(PropertyData pDa:pl.get(pd.getName())){		
					try{
						Double.parseDouble(pDa.getValue());
					} 
					catch(NumberFormatException nfe) {	
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	private static HashMap<String, ArrayList<PropertyData>> propertyListToHash(SearchResult<PropertyData> properties){
		HashMap<String, ArrayList<PropertyData>> hashProperties = new HashMap<String, ArrayList<PropertyData>>();
		for(PropertyData pd:properties){
			if(hashProperties.get(pd.getName())==null){
				hashProperties.put(pd.getName(), new ArrayList<PropertyData>());
			}			
			hashProperties.get(pd.getName()).add(pd);
		}
		
		return hashProperties;
	}
	
	private static PropertyGroup extractPropertyGroupFromType(Category type){
		PropertyGroup returnVal = new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
		
		for(PropertyGroup pg:type.getPropertyGroups()){
			for(PropertyDefinition pd:pg.getGroupElements()){
				returnVal.addGroupElement(pd);
			}
		}
		
		
		return returnVal;
	}
}

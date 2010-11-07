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
 * Filename: TypeDAO.java
 * Date: 15.07.2010
*/
package org.tagaprice.server.dao;

import java.util.ArrayList;

import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.Type;

public class TypeDAO  {
	protected DBConnection db;
	
	public TypeDAO(DBConnection db){
		this.db=db;
	}
	
	public void get(Type type){
		
		
		type.setSuperType(new Type("root", 9, 1, null));
		
		
		/*--------- EXAMPLE 
		if(type.getLocaleId()==9){			
			type = new Type("eisen", 9, 25, new Type("metall", 10, 15, new Type("werkzeug", 1, 5, new Type("root", 4, 1, null))));
			PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,0, 15, null,true)); 
			pg.addGroupElement(new PropertyDefinition(3L, 2, "protein", "Protein", 1, PropertyDefinition.Datatype.DOUBLE, -5, 20, null,true));
			pg.addGroupElement(new PropertyDefinition(4L, 3, "url", "URL", 1, PropertyDefinition.Datatype.STRING,-10, 25, null,false));
			type.addPropertyGroup(pg);
			PropertyGroup pg5 = new PropertyGroup("NUTRITIONFACTS", PropertyGroup.GroupType.LIST);
			pg5.addGroupElement(new PropertyDefinition(2L, 1, "ean", "BAR", 1, PropertyDefinition.Datatype.STRING, 1, 14, null,false)); 

			type.addPropertyGroup(pg5);
			
		*/
	}
	
	
	public ArrayList<Type> getTypeList(Type type){
		
		
		if(type==null)
			type = new Type("root", 9, 1, null);
		
		
		
		
		ArrayList<Type> types = new ArrayList<Type>();
	
		if(type.getTitle().equals("root")){
			types.add(new Type("nahrung", 9, -4, null));
			types.add(new Type("werkzeug", 9, -5, null));
			types.add(new Type("auto", 9, -6, null));
		}

		
		return types;
	}
}

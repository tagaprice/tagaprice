/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: TypeDraftServiceImpl.java
 * Date: 27.05.2010
*/
package org.tagaprice.server.rpc;

import java.util.ArrayList;

import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.rpc.TypeHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TypeHandlerImpl extends RemoteServiceServlet implements TypeHandler {

	@Override
	public Type get(long id) throws IllegalArgumentException {
		Type type;
		
		if(id==20){			
			type = new Type("eisen", 20, new Type("metall", 10, new Type("werkzeug", 5)));
			PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 1),true)); 
			pg.addGroupElement(new PropertyDefinition(3L, 2, "protein", "Protein", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 7, "g", 1),true));
			pg.addGroupElement(new PropertyDefinition(4L, 3, "url", "URL", 1, PropertyDefinition.Datatype.STRING,new Unit(15, 6, "g", 1),false));
			type.addPropertyGroup(pg);
		}else if(id==10){
			type = new Type("metall", 10, new Type("werkzeug", 5));
			PropertyGroup pg =new PropertyGroup("speedeigenschaften", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 1),true)); 
			pg.addGroupElement(new PropertyDefinition(3L, 2, "kw", "KW", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 7, "g", 1),true));
			pg.addGroupElement(new PropertyDefinition(4L, 3, "url", "URL", 1, PropertyDefinition.Datatype.STRING,new Unit(15, 6, "g", 1),false));
			type.addPropertyGroup(pg);
		}else if(id==5){			
			type=new Type("werkzeug", 5);
			PropertyGroup pg =new PropertyGroup("werkzeug", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "bla", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 1),true)); 
			type.addPropertyGroup(pg);
		}else{			
			type=new Type("auto", 6);
			PropertyGroup pg =new PropertyGroup("auto", PropertyGroup.GroupType.LIST);
			pg.addGroupElement(new PropertyDefinition(2L, 1, "bla", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 1),true)); 
			type.addPropertyGroup(pg);
		}
		
		
		/*
		PropertyGroup pg2 =new PropertyGroup("Random Stuff", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(7L, 5, "sodium", "Sodium", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 6, "g", 2),true));
		pg2.addGroupElement(new PropertyDefinition(5L, 6, "carbohydrate", "Carbohydrate", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 7, "g", 2),true)); 
		pg2.addGroupElement(new PropertyDefinition(6L, 7, "fat", "Fat", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 2),true));
		type.addPropertyGroup(pg2);
			
		PropertyGroup pg3 =new PropertyGroup("Produkt Angaben", PropertyGroup.GroupType.LIST);
		pg3.addGroupElement(new PropertyDefinition(7L, 3, "brand", "Brand", 3, PropertyDefinition.Datatype.STRING,new Unit(15, 11, "g", 2),true));
		pg3.addGroupElement(new PropertyDefinition(7L, 2, "ingredients", "Ingredients", 3, PropertyDefinition.Datatype.STRING,new Unit(15, 12, "g", 2),false));
		pg3.addGroupElement(new PropertyDefinition(7L, 1, "ean", "Barcode", 3, PropertyDefinition.Datatype.INT,new Unit(15, 13, "g", 2),false));
		type.addPropertyGroup(pg3);
		
		PropertyGroup pg4 =new PropertyGroup("Versionskontrolle", PropertyGroup.GroupType.LIST);
		pg4.addGroupElement(new PropertyDefinition(7L, 99, "created", "Created", 3, PropertyDefinition.Datatype.STRING,new Unit(15, 12, "g", 2),true));
		pg4.addGroupElement(new PropertyDefinition(7L, 98, "lastchange", "Latest changes", 3, PropertyDefinition.Datatype.STRING, new Unit(15, 12, "g", 2),true));
		type.addPropertyGroup(pg4);
		
		PropertyGroup pg5 = new PropertyGroup("NUTRITIONFACTS", PropertyGroup.GroupType.NUTRITIONFACTS);
		type.addPropertyGroup(pg5);
		*/
		
		
		return type;
	}

	@Override
	public ArrayList<Type> getTypeList(Type type)
			throws IllegalArgumentException {
		
		ArrayList<Type> types = new ArrayList<Type>();		
		
		if(type.getTitle().equals("root")){
			types.add(new Type("nahrung", 4));
			types.add(new Type("werkzeug", 5));
			types.add(new Type("auto", 6));
		}else if(type.getTitle().equals("nahrung")){
			types.add(new Type("flussig", 7, type));
			types.add(new Type("fest", 8, type));				
		}else if(type.getTitle().equals("werkzeug")){
			types.add(new Type("holz", 9, type));
			types.add(new Type("metall", 10, type));
		}else if(type.getTitle().equals("auto")){
			types.add(new Type("lkw", 11, type));
			types.add(new Type("pkw", 12, type));
			
		}else if(type.getTitle().equals("flussig")){
			types.add(new Type("milch", 13, type));
			types.add(new Type("tee", 14, type));
		}else if(type.getTitle().equals("fest")){
			types.add(new Type("rind", 15, type));
			types.add(new Type("pute", 16, type));
		}else if(type.getTitle().equals("holz")){
			types.add(new Type("kirsche", 17, type));
			types.add(new Type("birne", 18, type));
		}else if(type.getTitle().equals("metall")){
			types.add(new Type("alu", 19, type));
			types.add(new Type("eisen", 20, type));
		}else if(type.getTitle().equals("Food")){
			types.add(new Type("food1", 21, type));
			types.add(new Type("food2", 22, type));
		}
			
		
		return types;
	}
	
	

}

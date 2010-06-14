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
		
		Type type=new Type("Food");
		PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2L, 1, "Brennwert", PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g"),true)); 
		pg.addGroupElement(new PropertyDefinition(3L, 2, "Eiweiss", PropertyDefinition.Datatype.DOUBLE,new Unit(15, 7, "g"),true));
		pg.addGroupElement(new PropertyDefinition(4L, 3, "URL", PropertyDefinition.Datatype.STRING,new Unit(15, 6, "g"),false));
		type.addPropertyGroup(pg);
		
		
		PropertyGroup pg2 =new PropertyGroup("SachenEben", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(7L, 5, "Ballaststoffe", PropertyDefinition.Datatype.DOUBLE,new Unit(15, 6, "g"),true));
		pg2.addGroupElement(new PropertyDefinition(5L, 6, "Kohlenhydrate", PropertyDefinition.Datatype.DOUBLE,new Unit(15, 7, "g"),true)); 
		pg2.addGroupElement(new PropertyDefinition(6L, 7, "Fett", PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g"),true));
		type.addPropertyGroup(pg2);
			
		PropertyGroup pg3 =new PropertyGroup("Produkt Angaben", PropertyGroup.GroupType.LIST);
		pg3.addGroupElement(new PropertyDefinition(7L, 3, "Hersteller/Vertrieb", PropertyDefinition.Datatype.STRING,new Unit(15, 11, "g"),true));
		pg3.addGroupElement(new PropertyDefinition(7L, 2, "Inhaltsstoffe", PropertyDefinition.Datatype.STRING,new Unit(15, 12, "g"),false));
		pg3.addGroupElement(new PropertyDefinition(7L, 1, "EAN-Nummer", PropertyDefinition.Datatype.INT,new Unit(15, 13, "g"),false));
		type.addPropertyGroup(pg3);
		
		PropertyGroup pg4 =new PropertyGroup("Versionskontrolle", PropertyGroup.GroupType.LIST);
		pg4.addGroupElement(new PropertyDefinition(7L, 99, "Erfasst", PropertyDefinition.Datatype.STRING,new Unit(15, 4, "g"),true));
		pg4.addGroupElement(new PropertyDefinition(7L, 98, "Letzte Änderung", PropertyDefinition.Datatype.STRING,new Unit(15, 8, "g"),true));
		type.addPropertyGroup(pg4);
		return type;
	}

}

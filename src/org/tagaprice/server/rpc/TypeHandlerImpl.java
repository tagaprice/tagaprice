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

import org.tagaprice.client.TypeHandler;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.Unit;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TypeHandlerImpl extends RemoteServiceServlet implements TypeHandler {

	@Override
	public Type get(long id) throws IllegalArgumentException {
		
		Type type=new Type("Food");
		PropertyGroup pg =new PropertyGroup("NutritionFacts", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2L, "Brennwert", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true)); 
		pg.addGroupElement(new PropertyDefinition(3L, "Eiweiss", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		pg.addGroupElement(new PropertyDefinition(4L, "URL", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),false));
		type.addPropertyGroup(pg);
		
		
		PropertyGroup pg2 =new PropertyGroup("SachenEben", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(7L, "Ballaststoffe", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		pg2.addGroupElement(new PropertyDefinition(5L, "Kohlenhydrate", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true)); 
		pg2.addGroupElement(new PropertyDefinition(6L, "Fett", PropertyDefinition.Datatype.DOUBLE,new Unit(15, "g"),true));
		type.addPropertyGroup(pg2);
			
		PropertyGroup pg3 =new PropertyGroup("Produkt Angaben", PropertyGroup.GroupType.LIST);
		pg3.addGroupElement(new PropertyDefinition(7L, "Hersteller/Vertrieb", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		pg3.addGroupElement(new PropertyDefinition(7L, "Inhaltsstoffe", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),false));
		pg3.addGroupElement(new PropertyDefinition(7L, "EAN-Nummer", PropertyDefinition.Datatype.INT,new Unit(15, "g"),false));
		type.addPropertyGroup(pg3);
		
		PropertyGroup pg4 =new PropertyGroup("Versionskontrolle", PropertyGroup.GroupType.LIST);
		pg4.addGroupElement(new PropertyDefinition(7L, "Erfasst", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		pg4.addGroupElement(new PropertyDefinition(7L, "Letzte Änderung", PropertyDefinition.Datatype.STRING,new Unit(15, "g"),true));
		type.addPropertyGroup(pg4);
		return type;
	}

}

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
 * Filename: PropertyValidatorTest.java
 * Date: 05.07.2010
*/
package org.tagaprice.shared;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PropertyValidatorTest {
	Type type;
	
	
	@Before
	public void setUp() throws Exception {
		//Create Type
		type = new Type("eisen", 20, new Type("metall", 10, new Type("werkzeug", 5)));
		
		PropertyGroup pg = new PropertyGroup("Test1", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2L, 1, "ean", "ean", 1, PropertyDefinition.Datatype.INT,new Unit(15, 8, "g", 1),false)); 
		pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE,new Unit(15, 8, "g", 1),true)); 
		type.addPropertyGroup(pg);
		
		PropertyGroup pg2 = new PropertyGroup("Test2", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(2L, 1, "ps", "Ps", 1, PropertyDefinition.Datatype.INT,new Unit(15, 8, "g", 1),false)); 
		pg2.addGroupElement(new PropertyDefinition(2L, 1, "kw", "kw", 1, PropertyDefinition.Datatype.INT,new Unit(15, 8, "g", 1),true));
		type.addPropertyGroup(pg2);
		
		
		
	}
	
	@Test
	public void testCount(){
		
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("ean", "EAN", "1", new Unit(5, 2, "g", 3)));
		properties1.add(new PropertyData("ean", "EAN", "2", new Unit(5, 2, "g", 3)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//FALSE
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("energy", "Energy", "1", new Unit(5, 2, "g", 3)));
		properties2.add(new PropertyData("energy", "Energy", "2", new Unit(5, 2, "g", 3)));
		assertFalse(PropertyValidator.isValid(type, properties2));
	}
	
	
	@Test
	public void testInt(){
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("ean", "EAN", "1", new Unit(5, 2, "g", 3)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//ERROR
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("ean", "EAN", "2.2", new Unit(5, 2, "g", 3)));
		assertFalse(PropertyValidator.isValid(type, properties2));
		
		//ERROR
		SearchResult<PropertyData> properties3 = new SearchResult<PropertyData>();
		properties3.add(new PropertyData("ean", "EAN", "text", new Unit(5, 2, "g", 3)));
		assertFalse(PropertyValidator.isValid(type, properties3));
		
	}
	
	@Test
	public void testDouble(){
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("energy", "energy", "1", new Unit(5, 2, "g", 3)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//OK
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("energy", "energy", "2.2", new Unit(5, 2, "g", 3)));
		assertTrue(PropertyValidator.isValid(type, properties2));
		
		//ERROR
		SearchResult<PropertyData> properties3 = new SearchResult<PropertyData>();
		properties3.add(new PropertyData("energy", "energy", "text", new Unit(5, 2, "g", 3)));
		assertFalse(PropertyValidator.isValid(type, properties3));
		
	}
	

}

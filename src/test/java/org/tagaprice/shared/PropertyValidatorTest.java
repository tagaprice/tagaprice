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
	Category type;
	
	
	@Before
	public void setUp() throws Exception {
		//Create Type
		type = new Category("eisen", 20, 21, new Category("metall", 10, 11, new Category("werkzeug", 5, 6, null)));
		
		PropertyGroup pg = new PropertyGroup("Test1", PropertyGroup.GroupType.LIST);
		pg.addGroupElement(new PropertyDefinition(2L, 1, "ean", "ean", 1, PropertyDefinition.Datatype.INT,0, 29, new Unit(15, 8, "g", 1, null, 0),false)); 
		pg.addGroupElement(new PropertyDefinition(2L, 1, "energy", "Energy", 1, PropertyDefinition.Datatype.DOUBLE, 0, 29,new Unit(15, 8, "g", 1, null, 0),true)); 
		type.addPropertyGroup(pg);
		
		PropertyGroup pg2 = new PropertyGroup("Test2", PropertyGroup.GroupType.LIST);
		pg2.addGroupElement(new PropertyDefinition(2L, 1, "ps", "Ps", 1, PropertyDefinition.Datatype.INT, 0, 29, new Unit(15, 8, "g", 1, null, 0),false)); 
		pg2.addGroupElement(new PropertyDefinition(2L, 1, "kw", "kw", 1, PropertyDefinition.Datatype.INT, 0, 29, new Unit(15, 8, "g", 1, null, 0),true));
		type.addPropertyGroup(pg2);
		
		
		
	}
	
	@Test
	public void testCount(){
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("ean", "EAN", "1", new Unit(5, 2, "g", 3, null, 0)));
		properties1.add(new PropertyData("ean", "EAN", "2", new Unit(5, 2, "g", 3, null, 0)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//FALSE
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("energy", "Energy", "1", new Unit(5, 2, "g", 3, null, 0)));
		properties2.add(new PropertyData("energy", "Energy", "2", new Unit(5, 2, "g", 3, null, 0)));
		assertFalse(PropertyValidator.isValid(type, properties2));
	}
	
	
	@Test
	public void testInt(){
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("ean", "EAN", "1", new Unit(5, 2, "g", 3, null, 0)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//ERROR
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("ean", "EAN", "2.2", new Unit(5, 2, "g", 3, null, 0)));
		assertFalse(PropertyValidator.isValid(type, properties2));
		
		//ERROR
		SearchResult<PropertyData> properties3 = new SearchResult<PropertyData>();
		properties3.add(new PropertyData("ean", "EAN", "text", new Unit(5, 2, "g", 3, null, 0)));
		assertFalse(PropertyValidator.isValid(type, properties3));
		
	}
	
	@Test
	public void testDouble(){
		//OK
		SearchResult<PropertyData> properties1 = new SearchResult<PropertyData>();
		properties1.add(new PropertyData("energy", "energy", "1", new Unit(5, 2, "g", 3, null, 0)));
		assertTrue(PropertyValidator.isValid(type, properties1));
		
		//OK
		SearchResult<PropertyData> properties2 = new SearchResult<PropertyData>();
		properties2.add(new PropertyData("energy", "energy", "2.2", new Unit(5, 2, "g", 3, null, 0)));
		assertTrue(PropertyValidator.isValid(type, properties2));
		
		//ERROR
		SearchResult<PropertyData> properties3 = new SearchResult<PropertyData>();
		properties3.add(new PropertyData("energy", "energy", "text", new Unit(5, 2, "g", 3, null, 0)));
		assertFalse(PropertyValidator.isValid(type, properties3));
		
	}
	

}

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
 * Filename: PropertyDefinition.java
 * Date: 16.06.2010
*/
package org.tagaprice.shared;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PropertyDefinitionTest {

	private PropertyDefinition def;
	private String name = "name";
	private String title = "titel";
	
	@Before
	public void setUp() throws Exception{
		def = new PropertyDefinition(2L, 1, name, title, 1, PropertyDefinition.Datatype.DOUBLE, new Unit(15, 8, "g", 1),true);
	}
	
	
	@Test
	public void testTitle(){
		System.out.println(title+" | "+def.getTitle());
		assertTrue(title.equals(def.getTitle()));
	}
	
	@Test
	public void testName(){
		System.out.println(name+" | "+def.getName());
		assertTrue(name.equals(def.getName()));
	}
}

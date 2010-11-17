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
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;

public class PropertyDefinitionTest {

	private PropertyTypeDefinition def;
	private Long id=2l;
	private int rev=1;
	private String name = "name";
	private String title = "title";
	private Long creatorId = 13L;
	private Integer localeId=1;
	private Datatype type = PropertyTypeDefinition.Datatype.DOUBLE;
	private Integer minValue=0;
	private Integer maxValue=50;
	private Unit unit=new Unit(15, 8, "g", 1, null, 0);
	private boolean unique=true;
	
	@Before
	public void setUp() throws Exception{
		def = new PropertyTypeDefinition(
				id, 
				rev, 
				name, 
				title, 
				creatorId, 
				type, 
				minValue,
				maxValue,
				unit,
				unique);
		def.setLocaleId(localeId);
	}
	
	@Test
	public void testId(){
		assertEquals(id, def.getId());
	}
	
	@Test
	public void testRev(){
		assertEquals(rev, def.getRev());
	}
	
	
	@Test
	public void testTitle(){
		assertEquals(title, def.getTitle());
	}
	
	@Test
	public void testName(){
		assertEquals(name, def.getName());
	}
	
	@Test
	public void testLocaleId(){
		assertEquals(localeId, def.getLocaleId());
	}
	
	@Test
	public void testType(){
		assertEquals(type, def.getType());
	}
	
	@Test
	public void testMinValue(){
		assertEquals(minValue, def.getMinValue());
	}
	
	@Test
	public void testMaxValue(){
		assertEquals(maxValue, def.getMaxValue());
	}
	
	@Test
	public void testUnit() {
		assertEquals(unit, def.getUnit());
	}
	
	@Test
	public void testUnique(){
		assertEquals(unique, def.isUnique());
	}
}

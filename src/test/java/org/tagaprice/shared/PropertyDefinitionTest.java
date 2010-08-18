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
import org.tagaprice.shared.PropertyDefinition.Datatype;

public class PropertyDefinitionTest {

	private PropertyDefinition def;
	private Long id=2l;
	private int rev=1;
	private String name = "name";
	private String title = "titel";
	private int localeId=1;
	private Datatype type = PropertyDefinition.Datatype.DOUBLE;
	private int minValue=0;
	private int maxValue=50;
	private Unit unit=new Unit(15, 8, "g", 1, null, 0);
	private boolean unique=true;
	
	@Before
	public void setUp() throws Exception{
		def = new PropertyDefinition(
				id, 
				rev, 
				name, 
				title, 
				localeId, 
				type, 
				minValue,
				maxValue,
				unit,
				unique);
	}
	
	@Test
	public void testId(){
		assertTrue(id.equals(def.getId()));
	}
	
	@Test
	public void testRev(){
		assertTrue(rev==def.getRev());
	}
	
	
	@Test
	public void testTitle(){
		assertTrue(title.equals(def.getTitle()));
	}
	
	@Test
	public void testName(){
		assertTrue(name.equals(def.getName()));
	}
	
	@Test
	public void testLocaleId(){
		assertTrue(localeId==def.getLocaleId());
	}
	
	@Test
	public void testType(){
		assertTrue(type.equals(def.getType()));
	}
	
	@Test
	public void testMinValue(){
		assertTrue(minValue==def.getMinValue());
	}
	
	@Test
	public void testMaxValue(){
		assertTrue(maxValue==def.getMaxValue());
	}
	
	@Test
	public void testUnit() {
		// TODO add another Test here?
		fail("not yet implemented");
	}
	
	@Test
	public void testUnique(){
		assertTrue(unique==def.isUnique());
	}
}

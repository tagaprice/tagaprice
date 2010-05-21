/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: JsonDeSerializerTest.java
 * Date: 21.05.2010
*/
/**
 * 
 */
package org.tagaprice.server;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.Currency;

/**
 * Checks if Serializable-Objects stay the same after a serialization+deserialization-cycle.
 * Therefore the Serializable classes have to implement the equals() function.
 * @author Manuel Reithuber
 *
 */
public class JsonDeSerializerTest {
	private JsonSerializer serializer;
	private JsonDeserializer deserializer;
	ByteArrayOutputStream out = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
		serializer = new JsonSerializer(out);
		deserializer = new JsonDeserializer();
	}

	@Test
	public void testCurrency() throws Exception {
		Currency currency = new Currency(23, "EUR");
		serializer.put(currency);
		JSONObject json = new JSONObject(out.toString());
		Currency newCurrency = deserializer.getCurrency(json);
		
		assertEquals(currency, newCurrency);
	}
	
	@Test
	public void testCurrency_noName() throws Exception {
		Currency currency = new Currency(23, null);
		
		serializer.put(currency);
		JSONObject json = new JSONObject(out.toString());
		Currency newCurrency = deserializer.getCurrency(json);
		
		assertEquals(currency, newCurrency);
	}

	@Test
	public void testPrice() {
		fail("Not yet implemented");
	}

	@Test
	public void testProductData() {
		fail("Not yet implemented");
	}

	@Test
	public void testPropertyData() {
		fail("Not yet implemented");
	}

	@Test
	public void testPropertyList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuantity() {
		fail("Not yet implemented");
	}

	@Test
	public void testReceiptData() {
		fail("Not yet implemented");
	}

	@Test
	public void testServerResponse() {
		fail("Not yet implemented");
	}

	@Test
	public void tetShopData() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnit() {
		fail("Not yet implemented");
	}
}

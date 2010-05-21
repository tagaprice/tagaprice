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
import java.io.IOException;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.Currency;

/**
 * @author manuel
 *
 */
public class JsonDeSerializerTest {
	private JsonSerializer serializer;
	private JsonDeserializer deserializer;
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		serializer = new JsonSerializer(out);
		deserializer = new JsonDeserializer();
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.Currency)}.
	 * @throws IOException 
	 */
	@Test
	public void testPutCurrency() throws Exception {
		Currency currency = new Currency(23, "EUR");
		serializer.put(currency);
		JSONObject json = new JSONObject(out.toString());
		Currency newCurrency = deserializer.getCurrency(json);
		
		assertEquals(currency, newCurrency);
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.Price)}.
	 */
	@Test
	public void testPutPrice() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.ProductData)}.
	 */
	@Test
	public void testPutProductData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.PropertyData)}.
	 */
	@Test
	public void testPutPropertyData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.PropertyList)}.
	 */
	@Test
	public void testPutPropertyList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.Quantity)}.
	 */
	@Test
	public void testPutQuantity() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.ReceiptData)}.
	 */
	@Test
	public void testPutReceiptData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.ServerResponse)}.
	 */
	@Test
	public void testPutServerResponse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.ShopData)}.
	 */
	@Test
	public void testPutShopData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#put(org.tagaprice.shared.Unit)}.
	 */
	@Test
	public void testPutUnit() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.tagaprice.server.JsonSerializer#JsonSerializer(java.io.OutputStream)}.
	 */
	@Test
	public void testJsonSerializer() {
		fail("Not yet implemented");
	}

}

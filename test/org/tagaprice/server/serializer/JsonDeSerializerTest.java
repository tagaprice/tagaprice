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
package org.tagaprice.server.serializer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.serializer.JsonDeserializer;
import org.tagaprice.server.serializer.JsonSerializer;
import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyList;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.Unit;

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
		checkSerializer(new Currency(23, "EUR"));
	}
	
	@Test
	public void testCurrency_nullName() throws Exception {
		checkSerializer(new Currency(23, null));
	}

	@Test
	public void testPrice() throws Exception {
		checkSerializer(new Price(1234, 23, "EUR"));
	}
	
	@Test
	public void testPrice_nullCurrency() throws Exception {
		checkSerializer(new Price(1234, null));
	}

	@Test
	public void testProduct() throws IOException {
		checkSerializer(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noBrand() throws IOException {
		checkSerializer(new ProductData(23, -1, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noName() throws IOException {
		checkSerializer(new ProductData(23, 24, 25, null,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noImage() throws IOException {
		checkSerializer(new ProductData(23, 24, 25, "ACME Anvil 2t",
				null, 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noPrice() throws IOException {
		checkSerializer(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, null,
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noQuantity() throws IOException {
		checkSerializer(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				null, false));
	}

	@Test
	public void testProperty() throws IOException {
		checkSerializer(new PropertyData("name", "title", "value", new Unit(23, "unitName")));
	}

	@Test
	public void testProperty_nullValues() throws IOException {
		checkSerializer(new PropertyData(null, null, null, null));
	}

	@Test
	public void testPropertyList() throws IOException {
		PropertyList list = new PropertyList();
		list.add(new PropertyData("name", "title", "value", new Unit(23, "unit")));
		list.add(null);
		list.add(new PropertyData(null, null, null, null));

		checkSerializer(list);
	}
	
	@Test
	public void testPropertyLst_empty() throws IOException {
		checkSerializer(new PropertyList());
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
	
	
	public void checkSerializer(Serializable obj) throws IOException {
		try {
			serializer.putAny(obj);
			Serializable newObj = deserializer.getAny(out.toString(), obj.getSerializeName());

			assertEquals(obj, newObj);
		}
		catch (IOException e) {
			System.err.println("Invalid JSON:");
			System.err.println(out.toString());
			throw e;
		}
		catch (AssertionFailedError e) {
			System.err.println("the objects differ. serialization:");
			System.err.println(out.toString());
			throw e;
		}
	}
}

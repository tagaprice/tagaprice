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
package org.tagaprice.shared.serializer;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.Currency;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.serializer.JsonDeserializer;
import org.tagaprice.shared.serializer.JsonSerializer;

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
		doSerialize(new Currency(23, "EUR"));
	}
	
	@Test
	public void testCurrency_nullName() throws Exception {
		doSerialize(new Currency(23, null));
	}

	@Test
	public void testPrice() throws Exception {
		doSerialize(new Price(1234, 23, "EUR"));
	}
	
	@Test
	public void testPrice_nullCurrency() throws Exception {
		doSerialize(new Price(1234, null));
	}

	@Test
	public void testProduct() throws IOException {
		doSerialize(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noBrand() throws IOException {
		doSerialize(new ProductData(23, -1, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noName() throws IOException {
		doSerialize(new ProductData(23, 24, 25, null,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noImage() throws IOException {
		doSerialize(new ProductData(23, 24, 25, "ACME Anvil 2t",
				null, 80, 20, new Price(112584, 13, "EUR"),
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noPrice() throws IOException {
		doSerialize(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, null,
				new Quantity(2, new Unit(14, "t")), false));
	}

	@Test
	public void testProduct_noQuantity() throws IOException {
		doSerialize(new ProductData(23, 24, 25, "ACME Anvil 2t",
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, "EUR"),
				null, false));
	}

	@Test
	public void testProperty() throws IOException {
		doSerialize(new PropertyData("name", "title", "value", new Unit(23, "unitName")));
	}

	@Test
	public void testProperty_nullValues() throws IOException {
		doSerialize(new PropertyData(null, null, null, null));
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
	
	
	public void doSerialize(Serializable obj) throws IOException {
		serializer.putAny(obj);
		Serializable newObj = deserializer.getAny(out.toString(), obj.getSerializeName());
		
		assertEquals(obj, newObj);
	}
}

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
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.ServerResponse.StatusCode;

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
	
	private ProductData prod;

	@Before
	public void setUp() throws Exception {
		serializer = new JsonSerializer(out);
		deserializer = new JsonDeserializer();
		
		Unit u = new Unit(14, 4, "t", 1, null, 0);
		Quantity q = new Quantity(2, u);
		Currency c = new Currency(12, 2, "€", 13);
		prod = new ProductData(23L, 2, "ACME Anvil 2t", 1, 24L, 25L,
				"/img/foo/bar.jpg", q);
		prod.setRating(50);
		prod.setProgress(73);
		prod.setAvgPrice(new Price(254, c));
		prod._setLocaleId(-4);
		prod._setCreatorId(17L);
		u._setLocaleId(-3);
		u._setCreatorId(16L);
		c._setLocaleId(98);
		c._setCreatorId(1436L);

	}

	@Test
	public void testCurrency() throws Exception {
		Currency c = new Currency(23, 76, "EUR", 1);
		c._setLocaleId(-4);
		c._setCreatorId(123L);
		checkSerializer(c);
	}
	
	@Test
	public void testCurrency_nullName() throws Exception {
		Currency c = new Currency(23, 99, null, 1);
		c._setLocaleId(18);
		c._setCreatorId(11L);
		checkSerializer(c);
	}

	@Test
	public void testPrice() throws Exception {
		Currency c = new Currency(23, 5, "EUR", 1);
		Price p = new Price(1234, c);
		c._setLocaleId(17);
		c._setCreatorId(10L);
		checkSerializer(p);
	}
	
	@Test
	public void testPrice_nullCurrency() throws Exception {
		checkSerializer(new Price(1234, null));
	}

	@Test
	public void testProduct() throws IOException {
		
		checkSerializer(prod);
	}

	@Test
	public void testProduct_noBrand() throws IOException {
		prod.setBrandId(null);
		checkSerializer(prod);
	}

	@Test
	public void testProduct_noName() throws IOException {
		prod.setTitle(null);

		checkSerializer(prod);
	}

	@Test
	public void testProduct_noImage() throws IOException {
		prod.setImageSrc(null);
		checkSerializer(prod);
		
	}

	@Test
	public void testProduct_noAvgPrice() throws IOException {
		prod.setAvgPrice(null);
		checkSerializer(prod);
	}

	@Test
	public void testProduct_noQuantity() throws IOException {
		prod.setQuantity(null);
		checkSerializer(prod);
	}

	@Test
	public void testProperty() throws IOException {
		Unit unit = new Unit(23, 2, "unitName", 1, null, 0);
		PropertyData prop = new PropertyData("name", "title", "value", unit);
		unit._setLocaleId(15);
		unit._setCreatorId(-3L);
		unit._setRevCreatorId(-5L);
		
		checkSerializer(prop);
	}

	@Test
	public void testProperty_nullValues() throws IOException {
		checkSerializer(new PropertyData(null, null, null, null));
	}

	@Test
	public void testQuantity() throws IOException {
		Unit u = new Unit(23, 4, "unitName", 2, null, 0);
		u._setLocaleId(-18);
		u._setCreatorId(6L);

		checkSerializer(new Quantity(14, u));
	}
	
	@Test
	public void testQuantity_valueOnly() throws IOException {
		checkSerializer(new Quantity(15));
	}

	@Test
	public void testReceiptData() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchResult() throws Exception {
		SearchResult<Serializable> l = new SearchResult<Serializable>();
		Unit u = new Unit(-123L, 5, "unitName", 6, 15L, 100);
		u._setLocaleId(18);
		u._setCreatorId(-3L);
		Currency c = new Currency(2, 87, "EUR", 3);
		c._setLocaleId(17);
		c._setCreatorId(-4L);
		l.add(u);
		l.add(new Price(4289, c));
		checkSerializer(l);
	}

	@Test
	public void testServerResponse() throws IOException {
		Unit u = new Unit(42, 23, "answer", 0, null, 0);
		u._setLocaleId(88);
		u._setCreatorId(-88L);
		checkSerializer(new ServerResponse(StatusCode.Ok, u));
	}

	@Test
	public void testServerResponse_nullResponse() throws IOException {
		checkSerializer(new ServerResponse(StatusCode.NotFound, null));
	}

	@Test
	public void testServerResponse_nullStatus() throws IOException {
		checkSerializer(new ServerResponse(null, null));
	}

	@Test
	public void testServerResponse_accessDenied() throws IOException {
		checkSerializer(new ServerResponse(StatusCode.AccessDenied, null));
	}

	@Test
	public void testShopData() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnit() throws IOException {
		Unit u = new Unit(5L, 6, "unitName", 8, 20L, 123.45);
		u._setLocaleId(-4);
		u._setCreatorId(14L);
		checkSerializer(u);
	}
	
	@Test
	public void testUnit_nullName() throws IOException {
		Unit u = new Unit(5, 7, null, -1, null, 0);
		u._setLocaleId(987);
		u._setCreatorId(888L);
		checkSerializer(u);
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
			e.printStackTrace(System.err);
			throw e;
		}
		catch (AssertionFailedError e) {
			System.err.println("the objects differ. serialization:");
			System.err.println(out.toString());
			throw e;
		}
	}
}

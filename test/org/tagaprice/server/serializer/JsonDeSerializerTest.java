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

	@Before
	public void setUp() throws Exception {
		serializer = new JsonSerializer(out);
		deserializer = new JsonDeserializer();
	}

	@Test
	public void testCurrency() throws Exception {
		checkSerializer(new Currency(23, 76, "EUR", 1));
	}
	
	@Test
	public void testCurrency_nullName() throws Exception {
		checkSerializer(new Currency(23, 99, null, 1));
	}

	@Test
	public void testPrice() throws Exception {
		checkSerializer(new Price(1234, 23, 5, "EUR", 1));
	}
	
	@Test
	public void testPrice_nullCurrency() throws Exception {
		checkSerializer(new Price(1234, null));
	}

	@Test
	public void testProduct() throws IOException {
		checkSerializer(new ProductData(23, 2, "ACME Anvil 2t", 1, 24, 25,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, 3, "EUR", 1),
				new Quantity(2, new Unit(14, 4, "t", 1)), false));
	}

	@Test
	public void testProduct_noBrand() throws IOException {
		checkSerializer(new ProductData(23, 5, "ACME Anvil 2t", 2, -1, 25,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, 6, "EUR", 2),
				new Quantity(2, new Unit(14, 7, "t", 2)), false));
	}

	@Test
	public void testProduct_noName() throws IOException {
		checkSerializer(new ProductData(23, 5, null, -1, 24, 25,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, 12, "EUR", 3),
				new Quantity(2, new Unit(14, 13, "t", 3)), false));
	}

	@Test
	public void testProduct_noImage() throws IOException {
		checkSerializer(new ProductData(23, 8, "ACME Anvil 2t", 2, 24, 25,
				null, 80, 20, new Price(112584, 13, 1, "EUR", 2),
				new Quantity(2, new Unit(14, 17, "t", 2)), false));
	}

	@Test
	public void testProduct_noPrice() throws IOException {
		checkSerializer(new ProductData(23, 88, "ACME Anvil 2t", 2, 24, 25,
				"/img/foo/bar.jpg", 80, 20, null,
				new Quantity(2, new Unit(14, 7, "t", 18)), false));
	}

	@Test
	public void testProduct_noQuantity() throws IOException {
		checkSerializer(new ProductData(23, 4, "ACME Anvil 2t", 4, 24, 25,
				"/img/foo/bar.jpg", 80, 20, new Price(112584, 13, 6, "EUR", 4),
				null, false));
	}

	@Test
	public void testProperty() throws IOException {
		checkSerializer(new PropertyData("name", "title", "value", new Unit(23, 2, "unitName", 1)));
	}

	@Test
	public void testProperty_nullValues() throws IOException {
		checkSerializer(new PropertyData(null, null, null, null));
	}

	@Test
	public void testQuantity() throws IOException {
		checkSerializer(new Quantity(14, new Unit(23, 4, "unitName", 2)));
	}
	
	@Test
	public void testQuantity_valueOnly() throws IOException {
		checkSerializer(new Quantity(15));
	}
	
	@Test
	public void testQuantity_implicitUnit() throws IOException {
		checkSerializer(new Quantity(13, 14, 5, "unitName", 8));
	}

	@Test
	public void testReceiptData() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchResult() throws Exception {
		SearchResult<Serializable> l = new SearchResult<Serializable>();
		l.add(new Unit(-123L, 5, "unitName", 6, 15, 223));
		l.add(new Price(4289, 2, 87, "EUR", 3));
		checkSerializer(l);
	}

	@Test
	public void testServerResponse() throws IOException {
		checkSerializer(new ServerResponse(StatusCode.Ok, new Unit(42, 23, "answer", 0)));
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
		checkSerializer(new Unit(5L, 6, "unitName", 8, 20, 123.45));
	}
	
	@Test
	public void testUnit_nullName() throws IOException {
		checkSerializer(new Unit(5, 7, null, -1));
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

package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;

public class PriceTest {

	Price emptyPrice;
	Price newPrice;
	Price setterPrice;

	@Before
	public void setUp() throws Exception {
		emptyPrice = new Price();
		newPrice = new Price(new BigDecimal("15"), Currency.euro);
		setterPrice = new Price();
	}

	@Test
	public void testPrice() {
		assertNotNull(emptyPrice.getPrice());
		assertEquals(emptyPrice.getPrice(), new BigDecimal("0.0"));
		assertNull(emptyPrice.getCurrency());
	}

	@Test
	public void testSetPrice() {
		setterPrice.setPrice(new BigDecimal("18"));
		assertEquals(setterPrice.getPrice(), new BigDecimal("18"));
	}

	@Test
	public void testSetCurrency() {
		assertEquals(newPrice.getPrice(), new BigDecimal("15"));
	}

	@Test
	public void testGetPrice() {
		setterPrice.setCurrency(Currency.euro);
		assertEquals(setterPrice.getCurrency(), Currency.euro);
	}

	@Test
	public void testGetCurrency() {
		assertEquals(newPrice.getCurrency(), Currency.euro);
	}

}

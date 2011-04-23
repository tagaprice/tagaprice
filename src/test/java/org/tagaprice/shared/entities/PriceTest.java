package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

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
		newPrice = new Price(15, Currency.dkk);
		setterPrice = new Price();
	}

	@Test
	public void testPrice() {
		assertEquals(emptyPrice.getPrice(), 0);
		assertNull(emptyPrice.getCurrency());
	}

	@Test
	public void testSetPrice() {
		setterPrice.setPrice(18);
		assertEquals(setterPrice.getPrice(), 18);
	}

	@Test
	public void testSetCurrency() {
		assertEquals(newPrice.getPrice(), 15);
	}

	@Test
	public void testGetPrice() {
		setterPrice.setCurrency(Currency.euro);
		assertEquals(setterPrice.getCurrency(), Currency.euro);
	}

	@Test
	public void testGetCurrency() {
		assertEquals(newPrice.getCurrency(), Currency.dkk);
	}

}

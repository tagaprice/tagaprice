package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

public class ReceiptEntryTest {

	ReceiptEntry emptyReceipt;
	ReceiptEntry newReceipt;
	ReceiptEntry setterReceipt;

	@Before
	public void setUp() throws Exception {
		emptyReceipt = new ReceiptEntry();
		Package p = new Package(new Quantity(new BigDecimal("5.5"), new Unit("kg")));
		newReceipt = new ReceiptEntry(new Price(new BigDecimal(15), Currency.dkk), p);
		setterReceipt = new ReceiptEntry();
	}

	@Test
	public void testReceiptEntry() {
		assertNull(emptyReceipt.getPackage());
		assertNull(emptyReceipt.getPrice());
	}


	@Test
	public void testGetPackage() {
		assertNotNull(newReceipt.getPackage());
		assertEquals(newReceipt.getPackage().getQuantity().getQuantity(), new BigDecimal("5.5"));
		assertEquals(newReceipt.getPackage().getQuantity().getUnit().getTitle(), "kg");
	}

	@Test
	public void testGetPrice() {
		assertNotNull(newReceipt.getPrice());
		assertEquals(newReceipt.getPrice().getPrice(), new BigDecimal(15));
		assertEquals(newReceipt.getPrice().getCurrency(), Currency.dkk);
	}


	@Test
	public void testSetPackage() {
		assertNull(setterReceipt.getPackage());
		Package p2 = new Package(new Quantity(new BigDecimal("6.3"), new Unit("g")));
		setterReceipt.setPackage(p2);

		assertNotNull(setterReceipt.getPackage());
		assertEquals(setterReceipt.getPackage().getQuantity().getQuantity(), new BigDecimal("6.3"));
		assertEquals(setterReceipt.getPackage().getQuantity().getUnit().getTitle(), "g");
	}

	@Test
	public void testSetPrice() {
		assertNull(setterReceipt.getPrice());
		setterReceipt.setPrice(new Price(new BigDecimal(55), Currency.euro));
		assertNotNull(setterReceipt.getPrice());

		assertEquals(setterReceipt.getPrice().getPrice(), new BigDecimal(55));
		assertEquals(setterReceipt.getPrice().getCurrency(), Currency.euro);


		setterReceipt.setPrice(new Price(new BigDecimal(58), Currency.dkk));

		assertEquals(setterReceipt.getPrice().getPrice(), new BigDecimal(58));
		assertEquals(setterReceipt.getPrice().getCurrency(), Currency.dkk);

	}



}

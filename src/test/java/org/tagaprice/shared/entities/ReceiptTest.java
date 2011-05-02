package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;

public class ReceiptTest {

	Receipt emptyReceipt;
	Receipt newReceipt;
	Receipt updateReceipt;
	Receipt setterReceipt;

	User testUser = new User("testUID", "testRev", "Test User");
	
	@Before
	public void setUp() throws Exception {
		emptyReceipt = new Receipt();
		newReceipt = new Receipt(testUser, "newReceipt", new Date(1303575403414L), new Shop(testUser, "shop"));
		updateReceipt = new Receipt(testUser, "1", "2", "updateReceipt", new Date(1303575403414L), new Shop(testUser, "3", "4", "updateShop"));
		setterReceipt = new Receipt();
	}

	@Test
	public void testReceipt() {
		assertNull(emptyReceipt.getTitle());
		assertNull(emptyReceipt.getRevision());
		assertNull(emptyReceipt.getId());
		assertNull(emptyReceipt.getDate());
		assertNotNull(emptyReceipt.getReceiptEntries());
		assertEquals(emptyReceipt.getReceiptEntries().size(),0);
	}

	@Test
	public void testReceiptStringDateShop() {
		assertEquals(newReceipt.getTitle(), "newReceipt");
		assertNull(newReceipt.getRevision());
		assertNull(newReceipt.getId());
		assertEquals(newReceipt.getDate(), new Date(1303575403414L));
		assertEquals(newReceipt.getShop().getTitle(), "shop");
		assertNotNull(newReceipt.getReceiptEntries());
		assertEquals(newReceipt.getReceiptEntries().size(),0);

	}


	@Test
	public void testAddReceiptEntriy() {
		assertEquals(setterReceipt.getReceiptEntries().size(), 0);
		setterReceipt.setTitle("setterTitle");
		Package pack = new Package(new Quantity(new BigDecimal("15.3"), new Unit(testUser, "kg")));
		ReceiptEntry receipt = new ReceiptEntry(new Price(new BigDecimal(15), Currency.euro), pack);
		setterReceipt.addReceiptEntriy(receipt);
		assertEquals(setterReceipt.getReceiptEntries().size(), 1);
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPrice().getPrice(), new BigDecimal(15));
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPrice().getCurrency(), Currency.euro);
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPackage().getQuantity().getQuantity(), new BigDecimal("15.3"));
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPackage().getQuantity().getUnit().getTitle(), "kg");
	}

	@Test
	public void testGetShop() {
		assertEquals(updateReceipt.getShop().getTitle(), "updateShop");
		assertEquals(updateReceipt.getShop().getId(), "3");
		assertEquals(updateReceipt.getShop().getRevision(), "4");
	}

	@Test
	public void testGetReceiptEntries() {
		assertNotNull(updateReceipt.getReceiptEntries());
		assertEquals(updateReceipt.getReceiptEntries().size(),0);
	}

	@Test
	public void testGetShopId() {
		assertEquals(updateReceipt.getShopId(), "3");
	}

	@Test
	public void testSetShop() {
		setterReceipt.setShop(new Shop(testUser, "setterShop"));
		assertEquals(setterReceipt.getShop().getTitle(), "setterShop");
	}

	@Test
	public void testSetReceiptEntries() {
		assertEquals(setterReceipt.getReceiptEntries().size(), 0);
		setterReceipt.setTitle("setterTitle");

		ArrayList<ReceiptEntry> fullList = new ArrayList<ReceiptEntry>();
		Package pack1 = new Package(new Quantity(new BigDecimal("15.3"), new Unit(testUser, "kg")));
		ReceiptEntry receipt1 = new ReceiptEntry(new Price(new BigDecimal(15), Currency.euro), pack1);
		fullList.add(receipt1);

		Package pack2 = new Package(new Quantity(new BigDecimal("16.4"), new Unit(testUser, "g")));
		ReceiptEntry receipt2 = new ReceiptEntry(new Price(new BigDecimal(18), Currency.dkk), pack2);
		fullList.add(receipt2);

		setterReceipt.setReceiptEntries(fullList);
		assertEquals(setterReceipt.getReceiptEntries().size(), 2);

		assertEquals(setterReceipt.getReceiptEntries().get(0).getPrice().getPrice(), new BigDecimal(15));
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPrice().getCurrency(), Currency.euro);
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPackage().getQuantity().getQuantity(), new BigDecimal("15.3"));
		assertEquals(setterReceipt.getReceiptEntries().get(0).getPackage().getQuantity().getUnit().getTitle(), "kg");


		assertEquals(setterReceipt.getReceiptEntries().get(1).getPrice().getPrice(), new BigDecimal(18));
		assertEquals(setterReceipt.getReceiptEntries().get(1).getPrice().getCurrency(), Currency.dkk);
		assertEquals(setterReceipt.getReceiptEntries().get(1).getPackage().getQuantity().getQuantity(), new BigDecimal("16.4"));
		assertEquals(setterReceipt.getReceiptEntries().get(1).getPackage().getQuantity().getUnit().getTitle(), "g");


		setterReceipt.setReceiptEntries(new ArrayList<ReceiptEntry>());
		assertEquals(setterReceipt.getReceiptEntries().size(), 0);

	}

	@Test
	public void testSetDate() {
		setterReceipt.setDate(new Date(1303525403414L));
		assertEquals(setterReceipt.getDate(), new Date(1303525403414L));
	}

	@Test
	public void testGetDate() {
		assertEquals(updateReceipt.getDate(), new Date(1303575403414L));
	}

	@Test
	public void testGetTitle() {
		assertEquals(updateReceipt.getTitle(), "updateReceipt");
	}

	@Test
	public void testSetTitle() {
		setterReceipt.setTitle("setterTitle");
		assertEquals(setterReceipt.getTitle(), "setterTitle");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updateReceipt.getRevision(), "2");
	}

	@Test
	public void testSetRevision() {
		setterReceipt.setRevision("9");
		assertEquals(setterReceipt.getRevision(), "9");
	}

	@Test
	public void testGetId() {
		assertEquals(updateReceipt.getId(), "1");
	}

	@Test
	public void testSetId() {
		setterReceipt.setId("8");
		assertEquals(setterReceipt.getId(), "8");
	}

}

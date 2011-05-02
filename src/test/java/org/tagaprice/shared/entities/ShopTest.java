package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.shopmanagement.Shop;

public class ShopTest {

	Shop emptyShop;
	Shop newShop;
	Shop updateShop;
	Shop setterShop;
	
	User testUser = new User("testUID", "testRev", "Test User");

	@Before
	public void setUp() throws Exception {
		emptyShop = new Shop();
		newShop = new Shop(testUser, "title");
		updateShop = new Shop(testUser, "1", "2", "title");
		setterShop = new Shop();

	}

	@Test
	public void testShop() {
		assertEquals(emptyShop.getTitle(), null);
		assertEquals(emptyShop.getId(), null);
		assertEquals(emptyShop.getRevision(), null);
	}

	@Test
	public void testShopString() {
		assertEquals(newShop.getTitle(), "title");
		assertEquals(newShop.getId(), null);
		assertEquals(newShop.getRevision(), null);
	}

	@Test
	public void testSetParent() {
		Shop parent = new Shop(testUser, "1", "2", "title");
		setterShop.setParent(parent);
		assertNotNull(setterShop.getParent());
		assertEquals(setterShop.getParent().getTitle(), "title");
		assertEquals(setterShop.getParent().getId(), "1");
		assertEquals(setterShop.getParent().getRevision(), "2");
	}

	@Test
	public void testGetParent() {
		assertEquals(newShop.getParent(), null);
	}

	@Test
	public void testSetAddress() {
		setterShop.setAddress(new Address("address", 1.1, 2.2));
		assertEquals(setterShop.getAddress().getAddress(), "address");
		assertEquals(setterShop.getAddress().getLat(), 1.1, 0.0);
		assertEquals(setterShop.getAddress().getLng(), 2.2, 0.0);
	}

	@Test
	public void testGetAddress() {
		assertNotNull(newShop.getAddress());
		assertEquals(newShop.getAddress().getAddress(), null);
		assertEquals(newShop.getAddress().getLat(), 0.0, 0.0);
		assertEquals(newShop.getAddress().getLng(), 0.0, 0.0);
	}

	@Test
	public void testGetTitle() {
		assertEquals(updateShop.getTitle(), "title");
	}

	@Test
	public void testSetTitle() {
		newShop.setTitle("title");
		assertEquals(newShop.getTitle(), "title");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updateShop.getId(), "1");
	}

	@Test
	public void testSetRevision() {
		setterShop.setRevision("5");
		assertEquals(setterShop.getRevision(), "5");
	}

	@Test
	public void testGetId() {
		assertEquals(updateShop.getId(), "1");
	}

	@Test
	public void testSetId() {
		setterShop.setId("3");
		assertEquals(setterShop.getId(), "3");
	}

}

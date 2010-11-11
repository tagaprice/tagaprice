package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.data.AccountData;
import org.tagaprice.shared.data.Address;
import org.tagaprice.shared.data.ShopData;

public class ShopDAOTest {
	DBConnection db;
	ShopDAO dao;
	ShopData testShop;
	int localeId;
	long uid;

	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new ShopDAO(db);
		localeId = new LocaleDAO(db).get("English").getId();
		AccountData a = new AccountData("Testaccount", localeId, "bar@test.invalid", null);
		new AccountDAO(db).save(a);
		uid = a.getId();
		
		Address address = new Address("street", "city", new CountryDAO(db).get("us"), -2.564, 132.863);
		testShop = new ShopData("testshop title", localeId, uid, -16L, null, address);
		
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
		dao.save(testShop);
		ShopData shop = new ShopData(testShop.getId());
		dao.get(shop);
		assertEquals(testShop, shop);
	}
	
	@Test
	public void testRev() throws Exception {
		ShopData shop, shop1, shop2;
		shop = new ShopData("testshop title", localeId, uid, -16L, null, new Address());
		dao.save(shop);
		shop.setTitle("test-product v2");
		shop.setImageSrc("/foo/bar.png");
		dao.save(shop);
		assertEquals("shop revision should be 2 after two save() calls", 2, shop.getRev());
		
		shop2 = new ShopData(shop.getId());
		dao.get(shop2);
		assertEquals(shop, shop2);
		
		shop1 = new ShopData(shop.getId(), 1);
		dao.get(shop1);
		assertEquals(null, shop1.getImageSrc());
		assertEquals("shop1.title is wrong", "testshop title", shop1.getTitle());
	}

}

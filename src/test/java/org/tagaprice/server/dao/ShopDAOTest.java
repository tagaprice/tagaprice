package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.server.dao.postgres.AccountDAO;
import org.tagaprice.server.dao.postgres.CountryDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.server.dao.postgres.ShopDAO;
import org.tagaprice.shared.entities.Account;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Shop;

public class ShopDAOTest {
	DBConnection db;
	IShopDAO dao;
	Shop testShop;
	int localeId;
	long uid;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new ShopDAO(db);
		localeId = new LocaleDAO(db).get("English").getId();
		Account a = new Account("Testaccount", localeId, "bar@test.invalid", null);
		new AccountDAO(db).save(a);
		uid = a.getId();
		
		Address address = new Address("street", "city", new CountryDAO(db).get("us"), -2.564, 132.863);
		testShop = new Shop("testshop title", localeId, uid, -16L, null, address);
		
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
		dao.save(testShop);
		Shop shop = dao.getById(testShop.getId());
		assertEquals(testShop, shop);
	}
	
	@Test
	public void testRev() throws Exception {
		Shop shop, shop1, shop2;
		shop = new Shop("testshop title", localeId, uid, -16L, null, new Address());
		dao.save(shop);
		shop.setTitle("test-product v2");
		shop.setImageSrc("/foo/bar.png");
		dao.save(shop);
		assertEquals("shop revision should be 2 after two save() calls", 2, shop.getRev());
		
		shop2 = dao.getById(shop.getId());
		assertEquals(shop, shop2);
		
		shop1 = dao.getByIdAndRef(shop.getId(), 1);
		assertEquals(null, shop1.getImageSrc());
		assertEquals("shop1.title is wrong", "testshop title", shop1.getTitle());
	}

}

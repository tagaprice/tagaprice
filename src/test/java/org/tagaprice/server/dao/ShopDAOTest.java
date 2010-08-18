package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Account;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.ShopData;

public class ShopDAOTest {
	DBConnection db;
	ShopDAO dao;
	ShopData testShop;
	int localeId;
	long uid;

	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = ShopDAO.getInsance(db);
		localeId = LocaleDAO.getInstance(db).get("English").getId();
		Account a = new Account("Testaccount", localeId);
		AccountDAO.getInstance(db).save(a);
		uid = a.getId();
		
		Address address = new Address("street", "city", CountryDAO.getInstance(db).get("us"), -2.564, 132.863);
		testShop = new ShopData("testshop title", localeId, uid, -16L, null, address);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() throws Exception {
		dao.save(testShop);
		ShopData shop = new ShopData(testShop.getId());
		dao.get(shop);
		assertEquals(testShop, shop);
	}

}

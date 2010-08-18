package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Account;
import org.tagaprice.shared.ProductData;

public class ProductDAOTest {
	private ProductDAO dao;
	private DBConnection db;
	private int localeId;
	private long uid;

	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = ProductDAO.getInstance(db);
		localeId = LocaleDAO.getInstance(db).get("English").getId();
		Account a = new Account("testAccount", localeId);
		AccountDAO.getInstance(db).save(a);
		uid = a.getId();

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testCreate() throws Exception {
		ProductData prod = new ProductData("test-product", localeId, uid, null, null, null, null);
		dao.save(prod);
		ProductData prod2 = new ProductData(prod.getId());
		dao.get(prod2);
		assertEquals(prod, prod2);
	}

	@Test
	public void testSave() {
		fail("Not yet implemented"); // TODO
	}

}

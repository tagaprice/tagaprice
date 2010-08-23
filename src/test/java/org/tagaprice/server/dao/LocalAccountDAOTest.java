package org.tagaprice.server.dao;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.EntityDAOTest.TestDBConnection;
import org.tagaprice.shared.LocalAccountData;

public class LocalAccountDAOTest {
	private DBConnection db;
	private LocalAccountDAO dao;
	private int localeId;

	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		dao = LocalAccountDAO.getInstance(db);
		localeId = LocaleDAO.getInstance(db).get("English").getId();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCreate() throws Exception {
		LocalAccountData account = new LocalAccountData("testAccount", localeId, null, "mail@foo.invalid", "my secret password", null);
		dao.save(account);
		LocalAccountData a2 = new LocalAccountData(account.getId());
		assertEquals("Accounts don't match!", account, a2);
	}

}

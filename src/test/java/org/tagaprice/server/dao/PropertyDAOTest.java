package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.EntityDAOTest.TestEntity;
import org.tagaprice.shared.Account;

public class PropertyDAOTest {
	private TestEntity testEntity;
	private int localeId;
	private long uid;
	private DBConnection db;
	
	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		localeId = LocaleDAO.getInstance().get("en").getId();
		Account a = new Account("propertyTestAccount", localeId);
		AccountDAO.getInstance(db).save(a);
		uid = a.getId();
		testEntity = new TestEntity(16L, 1, "Title", localeId, uid);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGet() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSave() {
		fail("Not yet implemented"); // TODO
	}

}

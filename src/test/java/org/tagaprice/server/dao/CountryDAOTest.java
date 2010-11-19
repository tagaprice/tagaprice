package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.dao.EntityDAOTest.TestDBConnection;
import org.tagaprice.server.dao.postgres.CountryDAO;
import org.tagaprice.shared.entities.Country;

public class CountryDAOTest {
	TestDBConnection db;
	CountryDAO dao;
	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		dao = new CountryDAO(db);
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testGet() throws Exception {
		Country c = dao.getByCountryCode("us");
		assertEquals("us", c.getCode());
		assertEquals("United States", c.getTitle());
		assertEquals(null, c.getLocalTitle());
	}

}

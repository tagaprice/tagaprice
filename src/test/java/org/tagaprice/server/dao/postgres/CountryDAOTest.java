package org.tagaprice.server.dao.postgres;

import org.dbunit.dataset.IDataSet;
import org.junit.*;
import org.tagaprice.server.dao.helper.DatabaseManager;
import org.tagaprice.shared.entities.Country;

public class CountryDAOTest {
	IDataSet dataset;

	@Before
	public void setUp() throws Exception {
		/*
		 * This connects to the database and inserts the data for class Country.
		 * For later use, we store the inserted dataset
		 */
		dataset = DatabaseManager.setupEntitytable(Country.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetByCountryCode() {

	}

}

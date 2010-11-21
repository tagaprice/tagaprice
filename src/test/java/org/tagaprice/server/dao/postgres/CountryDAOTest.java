package org.tagaprice.server.dao.postgres;

import org.dbunit.dataset.*;
import org.junit.*;
import org.tagaprice.server.dao.helper.DatabaseManager;
import org.tagaprice.shared.entities.Country;
import org.tagaprice.shared.exception.DAOException;

public class CountryDAOTest {
	IDataSet dataset;
	CountryDAO countryDAO;
	ITable countriesTable;

	@Before
	public void setUp() throws Exception {
		/*
		 * This connects to the database and inserts the data for class Country.
		 * For later use, we store the inserted dataset
		 */
		dataset = DatabaseManager.setupEntitytable(Country.class);
		countriesTable = dataset.getTable("country");
		countryDAO = new CountryDAO(DatabaseManager.getDBConnection());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetByCountryCodeStandardCase() throws DataSetException {
		String countryCode = countriesTable.getValue(0, "country_code").toString();
		String title = countriesTable.getValue(0, "title").toString();
		String localtitle = countriesTable.getValue(0, "localtitle").toString();
		Country country = null;
		try {
			country = countryDAO.getByCountryCode(countryCode);
		} catch (DAOException e) {
			Assert.fail("CountryDAO threw an unexpected Exception");
		}
		Assert.assertNotNull("Existing country was not found", country);
		Assert.assertEquals(title, country.getTitle());
		Assert.assertEquals(localtitle, country.getLocalTitle());
	}

	@Test
	public void testGetByCountryCodeErrorCase() {
		String countryCode = "not_Exists";
		Country country = null;
		try {
			country = countryDAO.getByCountryCode(countryCode);
		} catch (DAOException e) {
			Assert.fail("CountryDAO threw an unexpected Exception");
		}
		Assert.assertNull(country);
	}

}

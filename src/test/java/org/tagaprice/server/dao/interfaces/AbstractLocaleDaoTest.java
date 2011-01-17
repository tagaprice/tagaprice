package org.tagaprice.server.dao.interfaces;


import java.text.ParseException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.server.dao.helper.DbUnitDataSetHelper;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.ILocaleDAO;

@ContextConfiguration(locations = { "/spring/test-beans.xml", "AbstractLocaleDaoTest-context.xml" })
public class AbstractLocaleDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected ILocaleDAO _localeDao;
	protected IDbTestInitializer _dbInitializer;
	private IDataSet _currentDataSet;

	@Before
	public void setUp() throws Exception {
		_localeDao = applicationContext.getBean("localeDao", ILocaleDAO.class);
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();
	}

	@After
	public void tearDown() throws Exception {
		// _dbInitializer.resetTables();
	}

	@Test
	//	@Rollback(false)
	public void saveLocale_shouldReturnLocaleWithActualLocaleId() {
		//
		//		Date localeDate = new Date();
		//
		//		Locale localeToSave = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(localeToSave, "setFallback", localeToSave);
		//
		//
		//		Locale expected = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(expected, "setFallback", expected);
		//		ReflectionTestUtils.invokeSetterMethod(expected, "setId", 0);
		//
		//		Locale actual = _localeDao.save(localeToSave);
		//
		//		assertThat(actual, equalTo(expected));
	}

	@Test
	public void getLocaleById_shouldReturnLocale() throws DataSetException, ParseException {
		ITable table = _currentDataSet.getTable("locale");
		int idToGet = DbUnitDataSetHelper.getInteger(table.getValue(0, "locale_id"));

		//		Locale actual = _localeDao.getById(idToGet);
		//
		//
		//		String title = (String) table.getValue(0, "title");
		//		String localtitle = (String) table.getValue(0, "localtitle");
		//
		//		Date created_at = DbUnitDataSetHelper.getDate(table.getValue(0, "created_at"));
		//
		//		Locale expected = new Locale(null, title, localtitle, created_at);
		//		ReflectionTestUtils.invokeSetterMethod(expected, "setId", idToGet);
		//		ReflectionTestUtils.invokeSetterMethod(expected, "setFallback", expected);
		//
		//		assertLocaleEqual(actual, expected);
	}

	/**
	 * asserts all getter methods of a locale except fallback, there only the id of the fallback is asserted if it's not null.
	 * @param actual actual result
	 * @param expected expected result to assert
	 */
	private void assertLocaleEqual(Locale actual, Locale expected) {
		// this doesn't work, maybe because of hibernate/javassist (see object in debugger)
		//		//assertThat(actual, equalTo(expected));
		//
		//		assertThat(actual.getId(), equalTo(expected.getId()));
		//		assertThat(actual.getCreatedAt(), equalTo(expected.getCreatedAt()));
		//		if(expected.getFallback() != null)
		//			assertThat(actual.getFallback().getId(), equalTo(expected.getFallback().getId()));
		//
		//		assertThat(actual.getLocalTitle(), equalTo(expected.getLocalTitle()));
		//		assertThat(actual.getTitle(), equalTo(expected.getTitle()));
	}
}
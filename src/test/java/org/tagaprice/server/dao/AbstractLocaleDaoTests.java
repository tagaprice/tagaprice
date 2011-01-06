package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.ILocaleDAO;

@ContextConfiguration(locations = { "/spring/test-beans.xml", "AbstractLocaleDaoTests-context.xml" })
public class AbstractLocaleDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

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
	@Rollback(false)
	public void saveLocale_shouldReturnLocaleWithActualLocaleId() {

		Date localeDate = new Date();

		Locale localeToSave = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		ReflectionTestUtils.invokeSetterMethod(localeToSave, "setFallback", localeToSave);


		Locale expected = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		ReflectionTestUtils.invokeSetterMethod(expected, "setFallback", expected);
		ReflectionTestUtils.invokeSetterMethod(expected, "setId", 0);

		Locale actual = _localeDao.save(localeToSave);

		assertThat(actual, equalTo(expected));
	}

	/**
	 * TODO this test fails while asserting column "created_at" for unknown reason
	 */
	@Test
	@Rollback(false)
	public void getLocaleById_shouldReturnLocale() throws DataSetException, ParseException {
		ITable table = _currentDataSet.getTable("locale");
		int idToGet = Integer.parseInt((String) table.getValue(0, "locale_id"));

		Locale actual = _localeDao.getById(idToGet);


		String title = (String) table.getValue(0, "title");
		String localtitle = (String) table.getValue(0, "localtitle");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
		Date created_at = dateFormatter.parse((String) table.getValue(0, "created_at"));

		Locale expected = new Locale(null, title, localtitle, created_at);
		ReflectionTestUtils.invokeSetterMethod(expected, "setId", idToGet);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getFallback().getId(), equalTo(table.getValue(0, "fallback_id")));
	}
}
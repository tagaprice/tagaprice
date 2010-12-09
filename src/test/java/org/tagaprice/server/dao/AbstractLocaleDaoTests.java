package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.tagaprice.core.beans.Locale;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.ints.ILocaleDAO;

@ContextConfiguration(locations = { "/spring/test-beans.xml", "AbstractLocaleDaoTests-context.xml" })
public class AbstractLocaleDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

	protected ILocaleDAO _localeDao;
	protected IDbTestInitializer _dbInitializer;

	@Before
	public void setUp() throws Exception {

		// TODO remove this and handle through xml ?
		_localeDao = applicationContext.getBean("localeDao", ILocaleDAO.class);
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();
	}

	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}

	@Test
	public void saveLocale_shouldReturnLocaleWithActualLocaleId() {

		Date localeDate = new Date();

		Locale localeToSave = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//ReflectionTestUtils.invokeSetterMethod(localeToSave, "setFallback", localeToSave);


		Locale expected = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//ReflectionTestUtils.invokeSetterMethod(expected, "setFallback", expected);
		ReflectionTestUtils.invokeSetterMethod(expected , "setId", 0);

		Locale actual = _localeDao.save(localeToSave);

		assertThat(actual, equalTo(expected));
	}
}
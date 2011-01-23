package org.tagaprice.server.dao.interfaces;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.dao.helper.IDbTestInitializer;

@ContextConfiguration
public class AbstractCategoryDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected ICategoryDAO _categoryDao;
	protected IDbTestInitializer _dbInitializer;
	private IDataSet _currentDataSet;

	private Logger _log = LoggerFactory.getLogger(AbstractCategoryDaoTest.class);

	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		// TODO this should be in setUpBeforeClass
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_categoryDao = applicationContext.getBean("categoryDao", ICategoryDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void saveCategory_shouldSave() throws Exception {
		_log.info("running test");

		Long id = 4L;

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category categoryToSave = new Category(id, "newRootCategory", null, HibernateSaveEntityCreator.getDefaultDate(), creator);

		Category actual = _categoryDao.save(categoryToSave);

		Category expected = new Category(id, "newRootCategory", null, HibernateSaveEntityCreator.getDefaultDate(), creator);

		assertThat(actual, is(expected));
	}

	@Test
	@Rollback(false)
	public void loadAllCategory_shouldLoadAllCategories() throws Exception {
		_log.info("running test");

		Long id = 4L;

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category categoryToSave = new Category(id, "newRootCategory", null, HibernateSaveEntityCreator.getDefaultDate(), creator);

		Category actual = _categoryDao.save(categoryToSave);

		Category expected = new Category(id, "newRootCategory", null, HibernateSaveEntityCreator.getDefaultDate(), creator);

		assertThat(actual, is(expected));
	}
}

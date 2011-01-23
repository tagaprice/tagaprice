package org.tagaprice.server.dao.interfaces;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

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
	@SuppressWarnings("unused")
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

		Long accountId = 1L;
		Long id = 4L;

		Category categoryToSave = HibernateSaveEntityCreator.createCategory(id, HibernateSaveEntityCreator.createAccount(accountId));

		Category actual = _categoryDao.save(categoryToSave);

		Category expected = HibernateSaveEntityCreator.createCategory(id, HibernateSaveEntityCreator.createAccount(accountId));

		assertThat(actual, is(expected));
	}

	@Test
	public void getAll_shouldReturnAllCategories() {
		_log.info("Running test");
		
		List<Category> actual = _categoryDao.getAll();
		
		
		List<Category> expected = new ArrayList<Category>();
		Account creator = HibernateSaveEntityCreator.createAccount(3L, "user@mail.com");
		Category root = HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", creator);
		expected.add(root);
		expected.add(HibernateSaveEntityCreator.createCategory(2L, root, "category1", creator));
		expected.add(HibernateSaveEntityCreator.createCategory(3L, root, "category2", creator));
		
		assertThat(actual, is(expected));
	}
}

package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.dbunit.dataset.IDataSet;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.interfaces.IShopDAO;

/**
 * Testcase to test the {@link IProductDAO} interface.
 * Extend this class for each concrete ORM technology.
 * 
 * TODO create AbstractDaoTest class
 * 
 * @author haja
 */
@ContextConfiguration
public class AbstractShopDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected IShopDAO _dao;
	protected IDbTestInitializer _dbInitializer;
	private Logger _log = LoggerFactory.getLogger(AbstractShopDaoTest.class);
	private IDataSet _currentDataSet;
	private SessionFactory _sessionFactory;


	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_dao = applicationContext.getBean("shopDao", IShopDAO.class);

		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);

		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}

	@After
	public void tearDown() throws Exception {
		// _dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void getShopById_shouldGetShop() throws Exception {
		_log.info("running test");

		long id = 0;
		String title = "testShop";
		Shop expected = new Shop(id, title);

		Shop actual = _dao.getShopById(id);

		//assertThat(actual, equalTo(expected)); this doesn't work, because of javassist dynamic subtyping
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
	}
}

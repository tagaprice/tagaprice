package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.hamcrest.Matcher;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.server.service.helper.EntityCreator;

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
	public void getShopById_shouldGetShopWithReceipts() throws Exception {
		_log.info("running test");

		long id = 0;
		String title = "testShop";

		Shop actual = _dao.getById(id);


		long receiptId = 0;
		HashSet<ReceiptEntry> receiptEntries = new HashSet<ReceiptEntry>();
		long prodId = 1;
		int prodRev = 1;
		int count = 1;
		long price = 10;
		receiptEntries.add(new ReceiptEntry(receiptId, prodId, prodRev, count, price, null));
		Receipt receipt = new Receipt(receiptId, id, EntityCreator.getDefaultDate(),
				HibernateSaveEntityCreator.createAccount(3L, "user@mail.com"), receiptEntries);

		Shop expected = new Shop(id, title, null);

		// assertThat(actual, equalTo(expected)); this doesn't work, because of javassist dynamic subtyping
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getReceipts(), hasItem(receipt));
	}

	@Test
	@Rollback(false)
	public void getBasicShopByTitle_shouldGetBasicShop() throws Exception {
		_log.info("running test");

		String titleToGet = "testShop";
		List<BasicShop> actual = _dao.getByTitle(titleToGet);

		BasicShop shopMatch1 = new BasicShop(0, "testShop");
		assertThat(actual, hasItem(shopMatch1));
		assertThat("result size", actual.size(), is(1));
	}

	@Test
	@Rollback(false)
	public void getByTitleFuzzy_shouldGetBasicShops() throws Exception {
		_log.info("running test");

		String titleToGet = "testShop";
		List<BasicShop> actual = _dao.getByTitleFuzzy(titleToGet);

		BasicShop shopMatch1 = new BasicShop(0, "testShop");
		BasicShop shopMatch2 = new BasicShop(1, "otherTestShopX");
		assertThat(actual, hasItems(shopMatch1, shopMatch2));
		assertThat("result size", actual.size(), is(2));
	}

	@Test
	@Rollback(false)
	public void saveShop_shouldPersist() throws Exception {
		_log.info("running test");

		long id = 5L;
		String title = "newTestShop";
		Shop shopToSave = new Shop(id, title, new HashSet<Receipt>());

		Shop actual = _dao.save(shopToSave);

		Shop expected = new Shop(id, title, new HashSet<Receipt>());

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(shopToSave);
	}
}

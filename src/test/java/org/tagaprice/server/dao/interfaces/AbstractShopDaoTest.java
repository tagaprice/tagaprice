package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.util.List;

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
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.service.helper.EntityCreator;

/**
 * Testcase to test the {@link IShopDAO} interface.
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

		long id = 1;

		Shop actual = _dao.getById(id);

		long receiptId = 1;
		ReceiptEntry receiptEntry1 = HibernateSaveEntityCreator.createReceiptEntry(
				HibernateSaveEntityCreator.createBasicReceipt(receiptId, 1L),
				HibernateSaveEntityCreator.createProductRevisionWithNullValues(1L, 1), 1, 10);
		ReceiptEntry receiptEntry2 = HibernateSaveEntityCreator.createReceiptEntry(
				HibernateSaveEntityCreator.createBasicReceipt(receiptId, 1L),
				HibernateSaveEntityCreator.createProductRevisionWithNullValues(2L, 2), 5, 100);

		Shop expected = HibernateSaveEntityCreator.createShop(id, "testShop");

		// assertThat(actual, equalTo(expected)); this doesn't work, because of javassist dynamic subtyping
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getReceiptEntries(), hasItems(receiptEntry1, receiptEntry2));
		assertThat(actual.getReceiptEntries().size(), is(2));
	}

	@Test
	@Rollback(false)
	public void getByTitle_shouldGetBasicShops() throws Exception {
		_log.info("running test");

		String titleToGet = "testShop";
		List<BasicShop> actual = _dao.getByTitle(titleToGet);

		BasicShop match1 = EntityCreator.createBasicShop(1L, "testShop");
		assertThat(actual, hasItem(match1));
		assertThat("result size", actual.size(), is(1));
	}

	@Test
	@Rollback(false)
	public void getByTitleFuzzy_shouldGetBasicShops() throws Exception {
		_log.info("running test");

		String titleToGet = "testShop";
		List<BasicShop> actual = _dao.getByTitleFuzzy(titleToGet);

		BasicShop match1 = EntityCreator.createBasicShop(1L, "testShop");
		BasicShop match2 = EntityCreator.createBasicShop(2L, "otherTestShopX");
		assertThat(actual, hasItems(match1, match2));
		assertThat("result size", actual.size(), is(2));
	}

	@Test
	public void getAll_shouldListBasicShops() throws Exception {
		_log.info("running test");

		List<BasicShop> actual = _dao.getAll();

		BasicShop match1 = EntityCreator.createBasicShop(1L, "testShop");
		BasicShop match2 = EntityCreator.createBasicShop(2L, "otherTestShopX");
		BasicShop match3 = EntityCreator.createBasicShop(3L, "myShop");

		assertThat(actual, hasItems(match1, match2, match3));
		assertThat(actual.size(), is(3));
	}

	@Test
	@Rollback(false)
	public void saveShop_shouldPersist() throws Exception {
		_log.info("running test");

		String title = "newTestShop";
		Shop shopToSave = HibernateSaveEntityCreator.createShop(null, title);

		Shop actual = _dao.save(shopToSave);

		long nextEmptyId = 4L;
		Shop expected = HibernateSaveEntityCreator.createShop(nextEmptyId, title);

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(shopToSave);
	}


	@Test
	@Rollback(false)
	public void updateExistingShop_shouldUpdate() throws Exception {
		_log.info("running test");

		long idToUpdate = 1; // this id must exist in testdata
		String title = "updatedTestShop";
		double latitude = 10.000;
		double longitude = 20.202;

		Shop shopToUpdate = HibernateSaveEntityCreator.createShop(idToUpdate, title, latitude, longitude);

		Shop actual = _dao.save(shopToUpdate);

		Shop expected = HibernateSaveEntityCreator.createShop(idToUpdate, title, latitude, longitude);

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(shopToUpdate);
	}


	@Test(expected = ServerException.class)
	@Rollback(false)
	public void saveShopWithNullTitle_shouldThrow() throws Exception {
		_log.info("running test");

		Shop shopToSave = HibernateSaveEntityCreator.createShop(null, null);
		_dao.save(shopToSave);
	}
}

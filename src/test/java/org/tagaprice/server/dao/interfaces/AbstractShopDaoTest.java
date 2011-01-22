package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.HashSet;
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
import org.tagaprice.core.entities.BasicReceipt;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.ReceiptEntry;
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
	public void getShopById_shouldGetShopWithReceipts() throws Exception {
		_log.info("running test");

		long id = 0;
		String title = "testShop";

		Shop actual = _dao.getById(id);

		long receiptId = 0;
		ProductRevision rev1 = new ProductRevision(1L, 1, null, null, null, null, null, null, null);
		ReceiptEntry receiptEntry1 = new ReceiptEntry(new BasicReceipt(receiptId, null), rev1, 1, 10);
		ProductRevision rev2 = new ProductRevision(2L, 2, null, null, null, null, null, null, null);
		ReceiptEntry receiptEntry2 = new ReceiptEntry(new BasicReceipt(receiptId, null), rev2, 5, 100);

		Shop expected = new Shop(id, title, null);

		// assertThat(actual, equalTo(expected)); this doesn't work, because of javassist dynamic subtyping
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getReceiptEntries(), hasItems(receiptEntry1, receiptEntry2));
		assertThat(actual.getReceiptEntries().size(), is(2));
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
		Shop shopToSave = new Shop(id, title, new HashSet<ReceiptEntry>());

		Shop actual = _dao.save(shopToSave);

		Shop expected = new Shop(id, title, new HashSet<ReceiptEntry>());

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(shopToSave);
	}
}

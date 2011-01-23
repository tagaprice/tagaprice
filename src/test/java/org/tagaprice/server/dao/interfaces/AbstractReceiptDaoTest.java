package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.BasicReceipt;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.service.helper.EntityCreator;

/**
 * Testcase to test the {@link IReceiptDAO} interface.
 * Extend this class for each concrete ORM technology.
 * 
 * @author haja
 */
@ContextConfiguration
public class AbstractReceiptDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected IReceiptDAO _receiptDao;
	protected IDbTestInitializer _dbInitializer;
	private Logger _log = LoggerFactory.getLogger(AbstractReceiptDaoTest.class);
	private IDataSet _currentDataSet;
	private SessionFactory _sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_receiptDao = applicationContext.getBean("receiptDao", IReceiptDAO.class);

		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);

		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}

	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}

	@Test
	public void getReceiptEntriesByProductIdAndRev_passRevisionNotStored_shouldReturnEmptyList() throws Exception {
		_log.info("running test");

		long productId = 1L;
		int revNumber = 2;
		List<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId, revNumber);

		assertThat(entries.isEmpty(), is(true));
	}

	@Test
	public void getReceiptEntriesByProductIdAndRev_passIdNotStored_shouldReturnEmptyList() throws Exception {
		_log.info("running test");

		long productId = 2L;
		int revNumber = 1;
		List<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId, revNumber);

		assertThat(entries.isEmpty(), is(true));
	}

	@Test
	@Rollback(false)
	public void getReceiptEntriesByProductIdAndRev_shouldReturnReceiptEntries() throws Exception {
		_log.info("running test");

		long productId = 1L;
		int revNumber = 1;
		List<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId, revNumber);

		long receiptId = 1;
		int count = 1;
		long price = 10;

		long receiptId2 = 2;
		int count2 = 3;
		long price2 = 8;

		long shopId1 = 1;
		String shopTitle1 = "testShop";
		BasicReceipt basicReceipt = HibernateSaveEntityCreator.createBasicReceipt(receiptId, HibernateSaveEntityCreator.createBasicShop(shopId1, shopTitle1));
		
		long shopId2 = 2;
		String shopTitle2 = "otherTestShopX";
		BasicReceipt basicReceipt2 = HibernateSaveEntityCreator.createBasicReceipt(receiptId2, HibernateSaveEntityCreator.createBasicShop(shopId2, shopTitle2));

		ProductRevision rev = HibernateSaveEntityCreator.createProductRevisionWithNullValues(productId, revNumber);

		ReceiptEntry entry1 = HibernateSaveEntityCreator.createReceiptEntry(basicReceipt, rev, count, price);
		ReceiptEntry entry2 = HibernateSaveEntityCreator.createReceiptEntry(basicReceipt2, rev, count2, price2);

		assertThat(entries, hasItems(entry1, entry2));
	}

	@Test
	@Rollback(false)
	public void saveEmptyReceipt_shouldSaveReceipt() throws Exception {
		fail();
//		long receiptId = 3L;
//		long shopId = 4L;
//		Date createdAt = EntityCreator.getDefaultDate();
//		Account creator = HibernateSaveEntityCreator.createAccount(5L);
//
//		Receipt receiptToSave = HibernateSaveEntityCreator.createReceipt(receiptId, shopId, createdAt, creator);
//
//		Receipt actual = _receiptDao.save(receiptToSave);
//
//		Receipt expected = HibernateSaveEntityCreator.createReceipt(receiptId, shopId, createdAt, creator);
//
//		assertThat(actual, is(expected));
//
//		_sessionFactory.getCurrentSession().flush();
//		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
	}

	@Test
	@Rollback(false)
	public void saveReceiptWithReceiptEntries_shouldSaveReceipt() throws Exception {
		fail();
//		long receiptId = 3L;
//		long shopId = 1L;
//		Date createdAt = EntityCreator.getDefaultDate();
//		Account creator = HibernateSaveEntityCreator.createAccount(1L);
//
//
//		Set<ReceiptEntry> receiptEntries = new HashSet<ReceiptEntry>();
//		long prod1Id = 1;
//		int prod1RevNr = 1;
//		receiptEntries.add(HibernateSaveEntityCreator.createReceiptEntry(receiptId, shopId, prod1Id, prod1RevNr, 1, 200));
//
//		long prod2Id = 2;
//		int prod2RevNr = 2;
//		receiptEntries.add(HibernateSaveEntityCreator.createReceiptEntry(receiptId, shopId, prod2Id, prod2RevNr, 5, 1000));
//
//
//		Receipt receiptToSave = HibernateSaveEntityCreator.createReceipt(receiptId, shopId, createdAt, creator, receiptEntries);
//
//		Receipt actual = _receiptDao.save(receiptToSave);
//
//
//		Receipt expected = HibernateSaveEntityCreator.createReceipt(receiptId, shopId, createdAt, creator, receiptEntries);
//
//		assertThat(actual, is(expected));
//
//		_sessionFactory.getCurrentSession().flush();
//		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
//		for (ReceiptEntry re : receiptEntries)
//			DbSaveAssertUtility.assertEntitySaved(re);
	}

	// TODO Implement
	@Test
	public void updateReceipt_shouldAddEntries() throws Exception {
		fail("not implemented");
	}
}

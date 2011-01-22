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
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
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
		// _dbInitializer.resetTables();
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

		long receiptId = 0;
		int count = 1;
		long price = 10;

		long receiptId2 = 3;
		int count2 = 4;
		long price2 = 50;

		long shopId = 0;
		String shopTitle = "testShop";

		BasicShop basicShop = new BasicShop(shopId, shopTitle);

		BasicReceipt basicReceipt = HibernateSaveEntityCreator.createBasicReceipt(receiptId, basicShop);
		BasicReceipt basicReceipt2 = HibernateSaveEntityCreator.createBasicReceipt(receiptId2, basicShop);

		ProductRevision rev = new ProductRevision(productId, revNumber, null, null, null, null, null, null, null);

		ReceiptEntry entry1 = new ReceiptEntry(basicReceipt, rev, count, price);
		ReceiptEntry entry2 = new ReceiptEntry(basicReceipt2, rev, count2, price2);

		assertThat(entries, hasItems(entry1, entry2));
	}

	@Test
	@Rollback(false)
	public void saveEmptyReceipt_shouldSaveReceipt() throws Exception {
		long id = 1L;
		long shopId = 1L;
		Date createdAt = EntityCreator.getDefaultDate();
		Account creator = HibernateSaveEntityCreator.createAccount(5L);

		Receipt receiptToSave = new Receipt(id, new BasicShop(shopId, "testTitle"), createdAt, creator, new HashSet<ReceiptEntry>());

		Receipt actual = _receiptDao.save(receiptToSave);

		Receipt expected = new Receipt(id, new BasicShop(shopId, "testTitle"), createdAt, creator, new HashSet<ReceiptEntry>());

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
	}

	@Test
	@Rollback(false)
	public void saveReceiptWithReceiptEntries_shouldSaveReceipt() throws Exception {
		long id = 1L;
		long shopId = 1L;
		Date createdAt = EntityCreator.getDefaultDate();
		Account creator = HibernateSaveEntityCreator.createAccount(3L);


		//	Not needed with current entityReceipt impl
		//		long prod1Id = 10L;
		//		Set<ProductRevision> prod1Revs = new HashSet<ProductRevision>();
		//		prod1Revs.add(HibernateSaveEntityCreator.createProductRevision(prod1Id, 1, creator, HibernateSaveEntityCreator.getDefaultUnit(), null));
		//		Product prod1 = HibernateSaveEntityCreator.createProduct(prod1Id, EntityCreator.createLocale(1), prod1Revs);
		//
		//		long prod2Id = 11L;
		//		Set<ProductRevision> prod2Revs = new HashSet<ProductRevision>();
		//		prod2Revs.add(HibernateSaveEntityCreator.createProductRevision(prod2Id, 1, creator, HibernateSaveEntityCreator.getDefaultUnit(), null));
		//		Product prod2 = HibernateSaveEntityCreator.createProduct(prod2Id, EntityCreator.createLocale(1), prod2Revs);


		Set<ReceiptEntry> receiptEntries = new HashSet<ReceiptEntry>();
		long prod1Id = 1;
		int prod1RevNr = 1;
		receiptEntries.add(new ReceiptEntry(HibernateSaveEntityCreator.createBasicReceipt(id, 1L), new ProductRevision(prod1Id, prod1RevNr, null, null, null, null, null, null, null), 1, 200));
		long prod2Id = 2;
		int prod2RevNr = 2;
		receiptEntries.add(new ReceiptEntry(HibernateSaveEntityCreator.createBasicReceipt(id, 1L), new ProductRevision(prod2Id, prod2RevNr, null, null, null, null, null, null, null), 5, 1000));

		Receipt receiptToSave = new Receipt(id, new BasicShop(shopId, "d"), createdAt, creator, receiptEntries);


		Receipt actual = _receiptDao.save(receiptToSave);


		Receipt expected = new Receipt(id, new BasicShop(shopId, "d"), createdAt, creator, receiptEntries);

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
		for(ReceiptEntry re : receiptEntries)
			DbSaveAssertUtility.assertEntitySaved(re);
	}

	//TODO Implement
	@Test
	public void updateReceipt_shouldAddEntries() throws Exception {
		fail("not implemented");
	}
}

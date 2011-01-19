package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
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
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
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
	}

	@After
	public void tearDown() throws Exception {
		// _dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void getReceiptEntriesByProductIdAndRev_shouldReturnReceiptEntries() throws Exception {
		_log.info("running test");

		long productId = 1L;
		int rev = 1;
		List<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId , rev );

		long receipt_id = 0;
		int count = 1;
		long price = 10;

		long receipt_id2 = 3;
		int count2 = 4;
		long price2 = 50;

		ReceiptEntry entry1 = new ReceiptEntry(receipt_id, productId, rev, count, price);
		ReceiptEntry entry2 = new ReceiptEntry(receipt_id2, productId, rev, count2, price2);

		assertThat(entries, hasItems(entry1, entry2));
	}

	@Test
	public void saveReceipt_shouldSaveReceipt() throws Exception {
		long id = 1L; // TODO check this settings with db setup
		long shopId = 1L;
		Date createdAt = EntityCreator.getDefaultDate();
		Account creator = HibernateSaveEntityCreator.createAccount(5L);

		Set<ReceiptEntry> receiptEntries = new HashSet<ReceiptEntry>();

		Receipt receiptToSave = new Receipt(id, shopId, createdAt, creator, receiptEntries);


		Receipt actual = _receiptDao.save(receiptToSave);


		Receipt expected = new Receipt(id, shopId, createdAt, creator, receiptEntries);

		assertThat(actual, is(expected));
	}

	//TODO Implement
	@Test
	public void updateReceipt_shouldAddEntries() throws Exception {
		fail("not implemented");
	}
}

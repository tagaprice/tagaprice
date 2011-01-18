package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.SortedSet;

import org.dbunit.dataset.IDataSet;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.helper.IDbTestInitializer;

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

		// TODO this should be in setUpBeforeClass
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
		SortedSet<ReceiptEntry> entries = _receiptDao.getReceiptEntriesByProductIdAndRev(productId , rev );

		// TODO adjust values here
		long receipt_id = 0;
		ProductRevision productRevision1 = null;
		int count = 1;
		long price = 1;
		ProductRevision productRevision2 = null;
		int count2 = 1;
		long price2 = 1;

		ReceiptEntry entry1 = new ReceiptEntry(receipt_id, productRevision1, count, price);
		ReceiptEntry entry2 = new ReceiptEntry(receipt_id, productRevision2, count2, price2);

		assertThat(entries, hasItems(entry1, entry2));
	}
}

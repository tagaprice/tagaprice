package org.tagaprice.server.integration;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.tagaprice.core.api.IReceiptService;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.service.templates.ReceiptServiceTestTemplate;

@ContextConfiguration
public class ReceiptManagementTest extends ReceiptServiceTestTemplate {

	private IDbTestInitializer _dbInitializer;

	@Before
	public void setUp() throws Exception {
		_receiptService = applicationContext.getBean("defaultReceiptService", IReceiptService.class);

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		DbSaveAssertUtility.setDataSource(applicationContext.getBean(DataSource.class));
	}

	@Override
	protected void saveEmptyReceipt_shouldSaveReceipt_DB_ASSERTS(Receipt receiptToSave) {
		// no flush needed
		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
	}

	@Override
	protected void saveReceiptWithReceiptEntries_shouldSaveReceipt_DB_ASSERTS(Receipt receiptToSave,
			Set<ReceiptEntry> receiptEntries){
		DbSaveAssertUtility.assertEntitySaved(receiptToSave);
		for (ReceiptEntry re : receiptEntries)
			DbSaveAssertUtility.assertEntitySaved(re);
	}



	//
	// methods not needed by this test class
	//
	@Override
	protected void getReceiptEntriesByProductIdAndRev_passProductRevisionIdsNotStored_shouldReturnEmptyList_DAO_SETUP(
			Long productId, Integer productRevision) {
	}

	@Override
	protected void getReceiptEntriesByProductIdAndRev_shouldReturnEntriesAsGivenByDaoButOnlyUnique_DAO_SETUP(
			Long productId, Integer productRevision, List<ReceiptEntry> entries) {
	}

	@Override
	protected void saveEmptyReceipt_shouldSaveReceipt_DAO_SETUP(Receipt receiptToSave) {
	}

	@Override
	protected void saveReceiptWithReceiptEntries_shouldSaveReceipt_DAO_SETUP(Receipt receipt) {
	}
}

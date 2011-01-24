package org.tagaprice.server.integration;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.service.DefaultReceiptService;
import org.tagaprice.server.service.templates.ReceiptServiceTestTemplate;

@ContextConfiguration
public class ReceiptTest extends ReceiptServiceTestTemplate {

	private IDbTestInitializer _dbInitializer;
	private SessionFactory _sessionFactory;

	@Before
	public void setUp() throws Exception {
		_receiptService = (DefaultReceiptService) applicationContext.getBean("defaultReceiptService");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setDataSource(applicationContext.getBean(DataSource.class));
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
}

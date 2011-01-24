package org.tagaprice.server.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;
import org.tagaprice.server.service.templates.ReceiptServiceTestTemplate;

@ContextConfiguration
public class DefaultReceiptServiceTest extends ReceiptServiceTestTemplate {

	@Before
	public void setUp() throws Exception {
		_receiptService = applicationContext.getBean("defaultReceiptService", DefaultReceiptService.class);
		_receiptDao = mock(IReceiptDAO.class);
		((DefaultReceiptService) _receiptService).setReceiptDAO(_receiptDao);
	}

	@Override
	protected void getReceiptEntriesByProductIdAndRev_passProductRevisionIdsNotStored_shouldReturnEmptyList_DAO_SETUP(Long productId, Integer productRevision) {
		when(_receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision)).thenReturn(new ArrayList<ReceiptEntry>());
	}

	@Override
	protected void getReceiptEntriesByProductIdAndRev_shouldReturnEntriesAsGivenByDaoButOnlyUnique_DAO_SETUP(Long productId, Integer productRevision, List<ReceiptEntry> entries) {
		when(_receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision)).thenReturn(entries);
	}

	@Override
	protected void saveEmptyReceipt_shouldSaveReceipt_DB_ASSERTS(Receipt receiptToSave) {
	}

	@Override
	protected void saveEmptyReceipt_shouldSaveReceipt_DAO_SETUP(Receipt receiptToSave) {
		when(_receiptDao.save(receiptToSave)).thenReturn(receiptToSave);
	}

	@Override
	protected void saveReceiptWithReceiptEntries_shouldSaveReceipt_DAO_SETUP(Receipt receipt) 	{
		when(_receiptDao.save(receipt)).thenReturn(receipt);
	}


	//
	// methods not needed
	//

	@Override
	protected void saveReceiptWithReceiptEntries_shouldSaveReceipt_DB_ASSERTS(Receipt receiptToSave,
			Set<ReceiptEntry> receiptEntries) {
	}
}

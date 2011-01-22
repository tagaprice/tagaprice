package org.tagaprice.server.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;
import org.tagaprice.server.service.helper.EntityCreator;

@ContextConfiguration
public class DefaultReceiptServiceTest extends AbstractJUnit4SpringContextTests {
	private DefaultReceiptService _receiptService;
	private IReceiptDAO _receiptDao;
	
	@Before
	public void setUp() throws Exception {
		_receiptService = applicationContext.getBean("defaultReceiptService", DefaultReceiptService.class);
		_receiptDao = mock(IReceiptDAO.class);
		_receiptService.setReceiptDAO(_receiptDao);
	}
	
	@Test
	public void getReceiptEntriesByProductIdAndRev_passProductRevisionIdsNotStored_shouldReturnEmptyList() throws ServerException {
		Long productId = 1L;
		Integer productRevision = 2;
		
		when(_receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision)).thenReturn(new ArrayList<ReceiptEntry>());
		
		List<ReceiptEntry> receiptEntries = _receiptService.getReceiptEntriesByProductIdAndRev(productId, productRevision);
		
		assertThat(receiptEntries.isEmpty(), is(true));
	}
	
	//TODO uncomment and implement
//	@Test
//	public void getReceiptEntriesByProductIdAndRev_shouldReturnEntriesAsGivenByDaoButOnlyUnique() throws ServerException {
//		Long productId = 1L;
//		Integer productRevision = 2;
//		
//		ArrayList<ReceiptEntry> entries = new ArrayList<ReceiptEntry>();
//		ReceiptEntry entry1 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(1L, EntityCreator.createBasicShop(1L, "billa")), EntityCreator.createProductRevision(productId, productRevision), 10, 50);
//		entries.add(entry1);
//		ReceiptEntry entry2 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(2L, EntityCreator.createBasicShop(2L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 20, 40);
//		entries.add(entry2);
//		ReceiptEntry entry3 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(3L, EntityCreator.createBasicShop(3L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 15, 60);
//		entries.add(entry3);
//		ReceiptEntry entry4EqualToEntry3 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(4L, EntityCreator.createBasicShop(3L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 15, 60);
//		entries.add(entry4EqualToEntry3);
//		
//		when(_receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision)).thenReturn(entries);
//		
//		List<ReceiptEntry> actualReceiptEntries = _receiptService.getReceiptEntriesByProductIdAndRev(productId, productRevision);
//		
//		List<ReceiptEntry> expectedEntries = new ArrayList<ReceiptEntry>();
//		ReceiptEntry expectedEntry1 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(1L, EntityCreator.createBasicShop(1L, "billa")), EntityCreator.createProductRevision(productId, productRevision), 10, 50);
//		expectedEntries.add(expectedEntry1);
//		ReceiptEntry expectedEntry2 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(2L, EntityCreator.createBasicShop(2L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 20, 40);
//		expectedEntries.add(expectedEntry2);
//		ReceiptEntry expectedEntry3 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(3L, EntityCreator.createBasicShop(3L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 15, 60);
//		expectedEntries.add(expectedEntry3);
//		
//			
//		assertThat(actualReceiptEntries, is(expectedEntries));
//	}
	
	@Test
	public void getReceiptEntriesByProductIdAndRev_shouldReturnEntriesAsGivenByDaoButOnlyUnique() throws ServerException {
		Long productId = 1L;
		Integer productRevision = 2;
		
		ArrayList<ReceiptEntry> entries = new ArrayList<ReceiptEntry>();
		ReceiptEntry entry1 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(1L, EntityCreator.createBasicShop(1L, "billa")), EntityCreator.createProductRevision(productId, productRevision), 10, 50);
		entries.add(entry1);
		ReceiptEntry entry2 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(2L, EntityCreator.createBasicShop(2L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 20, 40);
		entries.add(entry2);
		ReceiptEntry entry3 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(3L, EntityCreator.createBasicShop(3L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 15, 60);
		entries.add(entry3);
		
		when(_receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision)).thenReturn(entries);
		
		List<ReceiptEntry> actualReceiptEntries = _receiptService.getReceiptEntriesByProductIdAndRev(productId, productRevision);
		
		List<ReceiptEntry> expectedEntries = new ArrayList<ReceiptEntry>();
		ReceiptEntry expectedEntry1 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(1L, EntityCreator.createBasicShop(1L, "billa")), EntityCreator.createProductRevision(productId, productRevision), 10, 50);
		expectedEntries.add(expectedEntry1);
		ReceiptEntry expectedEntry2 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(2L, EntityCreator.createBasicShop(2L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 20, 40);
		expectedEntries.add(expectedEntry2);
		ReceiptEntry expectedEntry3 = EntityCreator.createReceiptEntry(EntityCreator.createBasicReceipt(3L, EntityCreator.createBasicShop(3L, "hofer")), EntityCreator.createProductRevision(productId, productRevision), 15, 60);
		expectedEntries.add(expectedEntry3);
		
			
		assertThat(actualReceiptEntries, is(expectedEntries));
	}
}

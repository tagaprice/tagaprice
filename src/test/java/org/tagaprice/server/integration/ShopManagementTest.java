package org.tagaprice.server.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.IShopService;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.service.helper.EntityCreator;


@ContextConfiguration
public class ShopManagementTest extends AbstractJUnit4SpringContextTests {
	private IShopService _shopService;
	private IDbTestInitializer _dbInitializer;

	@Before
	public void setUp() throws Exception {
		_shopService = applicationContext.getBean("defaultShopService", IShopService.class);

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		DbSaveAssertUtility.setDataSource(applicationContext.getBean(DataSource.class));
	}

	@Test
	public void saveNewShop_shouldReturnShopWithIdSet() throws Exception {
		long nextFreeId = HibernateSaveEntityCreator.nextFreeShopId;

		Shop toSave = EntityCreator.createShop(null);

		Shop actual = _shopService.save(toSave);

		Shop expected = EntityCreator.createShop(nextFreeId);

		assertThat(actual, equalTo(expected));


	}

	@Test(expected = IllegalArgumentException.class)
	public void saveNullShop_shouldThrow() throws Exception {
		_shopService.save(null);
	}

	@Test
	public void getShopById_shouldGetShop() throws Exception {
		Long id = 1L;

		Shop actual = _shopService.getById(id);

		Long receiptId = 1L;
		ReceiptEntry entry1 = HibernateSaveEntityCreator.createReceiptEntry(receiptId , id, 1, 1, 1, 10);
		ReceiptEntry entry2 = HibernateSaveEntityCreator.createReceiptEntry(receiptId , id, 2, 2, 5, 100);;

		HashSet<ReceiptEntry> expectedRecieptEntries = new HashSet<ReceiptEntry>();
		expectedRecieptEntries.add(entry1);
		expectedRecieptEntries.add(entry2);

		Shop expected = HibernateSaveEntityCreator.createShop(id, "testShop", 10.555, 20.111, expectedRecieptEntries );

		assertShop(actual, expected);
	}


	@Test
	public void getShopById_shopNotFound_shouldReturnNull() throws Exception {
		long shopIdNotInDb = 100L;
		Shop actual = _shopService.getById(shopIdNotInDb);

		assertThat(actual, equalTo(null));
	}


	@Test
	public void getShopByTitle_daoReturnsEmptyList_shoudGetEmptyList() throws Exception {
		List<BasicShop> actual = _shopService.getByTitle("someTitle");

		assertThat(actual.isEmpty(), is(true));
	}

	@Test
	public void getShopByTitle_shoudGetBasicShops() throws Exception {
		List<BasicShop> actual = _shopService.getByTitle("testShop");

		BasicShop expectedShop = HibernateSaveEntityCreator.createBasicShop(1L, "testShop");

		assertThat(actual.size(), is(1));
		assertThat(actual.get(0), is(expectedShop));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getShopByTitle_titleNull_shouldThrow() throws Exception {
		_shopService.getByTitle(null);
	}


	@Test
	public void getShopByTitleFuzzy_shouldReturnBasicShops() throws Exception {
		List<BasicShop> actual = _shopService.getByTitleFuzzy("test");

		List<BasicShop> expected = new LinkedList<BasicShop>();
		expected.add(HibernateSaveEntityCreator.createBasicShop(1L, "testShop"));
		expected.add(HibernateSaveEntityCreator.createBasicShop(2L, "otherTestShopX"));

		assertThat(actual.size(), is(expected.size()));
		for (BasicShop s : expected)
			assertThat(actual, hasItem(s));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getShopByTitleFuzzy_titleNull_shouldThrow() throws Exception {
		_shopService.getByTitleFuzzy(null);
	}



	@Test
	public void getAll_shouldReturnBasicShops() throws Exception {

		List<BasicShop> actual = _shopService.getAll();

		BasicShop shop1 = EntityCreator.createBasicShop(1L, "testShop");
		BasicShop shop2 = EntityCreator.createBasicShop(2L, "otherTestShopX");
		BasicShop shop3 = EntityCreator.createBasicShop(3L, "myShop");

		assertThat(actual, hasItems(shop1, shop2, shop3));
		assertThat(actual.size(), is(3));
	}

	//
	// helpers
	//

	/**
	 * this is a WORKAROUND for assertThat(actual, is(expected)) because of javassistLazyHandler
	 */
	private void assertShop(Shop actual, Shop expected) {
		assertThat(actual.getId(), is(expected.getId()));
		assertThat(actual.getTitle(), is(expected.getTitle()));
		assertThat(actual.getLatitude(), is(expected.getLatitude()));
		assertThat(actual.getLongitude(), is(expected.getLongitude()));
		assertThat(actual.getReceiptEntries(), is(expected.getReceiptEntries()));

	}
}

package org.tagaprice.server.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.IShopService;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
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
		long nextFreeId = 4L;

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
		Shop actual = _shopService.getById(1L);

		Shop expected = EntityCreator.createShop(1L);

		assertThat(actual, is(expected));
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

	// @Test
	// public void getShopByTitle_shoudGetBasicShops() throws Exception {
	//
	// List<BasicShop> actual = _shopService.getByTitle("test");
	//
	// for (BasicShop s : list)
	// assertThat(actual, hasItem(s));
	// assertThat(actual.size(), is(list.size()));
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void getShopByTitle_titleNull_shouldThrow() throws Exception {
	// _shopService.getByTitle(null);
	// }
	//
	//
	// @Test
	// public void getShopByTitleFuzzy_shouldReturnBasicShops() throws Exception {
	//
	// List<BasicShop> actual = _shopService.getByTitleFuzzy("test");
	//
	// for (BasicShop s : list)
	// assertThat(actual, hasItem(s));
	// assertThat(actual.size(), is(list.size()));
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void getShopByTitleFuzzy_titleNull_shouldThrow() throws Exception {
	// _shopService.getByTitleFuzzy(null);
	// }
	//
	//
	//
	// @Test
	// public void getAll_shouldReturnBasicShops() throws Exception {
	//
	// List<BasicShop> actual = _shopService.getAll();
	//
	// BasicShop shop1 = EntityCreator.createBasicShop(1L, "test1");
	// BasicShop shop2 = EntityCreator.createBasicShop(2L, "test2");
	// BasicShop shop3 = EntityCreator.createBasicShop(3L, "test3");
	//
	// assertThat(actual, hasItems(shop1, shop2, shop3));
	// assertThat(actual.size(), is(3));
	// }
}

package org.tagaprice.server.service;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;


@ContextConfiguration
public class DefaultShopServiceTest extends AbstractJUnit4SpringContextTests {
	private DefaultShopService _shopService;
	private IShopDAO _shopDaoMock;


	@Before
	public void setUp() throws Exception {
		_shopService = applicationContext.getBean("defaultShopService", DefaultShopService.class);
		_shopDaoMock = mock(IShopDAO.class);
		_shopService.setShopDAO(_shopDaoMock); // TODO replace this by dependency injection via autowire, how to inject
		// mock ? see
		// http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean
		// for help
	}

	@Test
	public void saveNewShop_shouldReturnShopWithIdSet() throws Exception {
		Shop toSave = EntityCreator.createShop(null);

		// Mock sets id and returns whatever it gets
		when(_shopDaoMock.save((Shop) any())).thenAnswer(new Answer<Shop>() {
			@Override
			public Shop answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Shop shop = (Shop) args[0];
				return new Shop(1L, shop.getTitle(), shop.getLatitude(), shop.getLongitude(), shop.getReceiptEntries());
			}
		});

		Shop actual = _shopService.save(toSave);

		Shop expected = EntityCreator.createShop(1L);

		assertThat(actual, equalTo(expected));
	}

	@Test(expected = IllegalArgumentException.class)
	public void saveNullShop_shouldThrow() throws Exception {
		try {
			_shopService.save(null);
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			verify(_shopDaoMock, never()).save(Matchers.any(Shop.class));
		}
	}

	@Test
	public void getShopById_shouldGetShop() throws Exception {
		// mock returns a new shop with argument id
		when(_shopDaoMock.getById(anyLong())).thenAnswer(new Answer<Shop>() {
			@Override
			public Shop answer(InvocationOnMock invocation) throws Throwable {
				Long id = (Long) invocation.getArguments()[0];
				return EntityCreator.createShop(id);
			}
		});

		Shop actual = _shopService.getById(1L);

		Shop expected = EntityCreator.createShop(1L);

		assertThat(actual, is(expected));
	}


	@Test
	public void getShopByTitle_daoReturnsEmptyList_shoudGetEmptyList() throws Exception {
		// mock returns empty list
		when(_shopDaoMock.getByTitle(anyString())).thenAnswer(new Answer<List<BasicShop>>() {
			@Override
			public List<BasicShop> answer(InvocationOnMock invocation) throws Throwable {
				return new LinkedList<BasicShop>();
			}
		});

		List<BasicShop> actual = _shopService.getByTitle("someTitle");

		assertThat(actual.isEmpty(), is(true));
	}

	@Test
	public void getShopByTitle_shoudGetBasicShops() throws Exception {
		final List<BasicShop> list = new LinkedList<BasicShop>();
		list.add(EntityCreator.createBasicShop(1L, "test"));
		list.add(EntityCreator.createBasicShop(5L, "test"));

		// mock returns new basicShops if argument is "test"
		when(_shopDaoMock.getByTitle(anyString())).thenAnswer(new Answer<List<BasicShop>>() {
			@Override
			public List<BasicShop> answer(InvocationOnMock invocation) throws Throwable {
				if (((String) invocation.getArguments()[0]).equals("test"))
					return list;
				return new LinkedList<BasicShop>();
			}
		});

		List<BasicShop> actual = _shopService.getByTitle("test");

		for (BasicShop s : list)
			assertThat(actual, hasItem(s));
		assertThat(actual.size(), is(list.size()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getShopByTitle_titleNull_shouldThrow() throws Exception {
		try {
			_shopService.getByTitle(null);
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			verify(_shopDaoMock, never()).getByTitle(anyString());
		}
	}


	@Test
	public void getShopByTitleFuzzy_shouldReturnBasicShops() throws Exception {
		final List<BasicShop> list = new LinkedList<BasicShop>();
		list.add(EntityCreator.createBasicShop(1L, "test1"));
		list.add(EntityCreator.createBasicShop(2L, "test2"));
		list.add(EntityCreator.createBasicShop(3L, "3test"));

		// mock returns new basicShops if argument contains "test"
		when(_shopDaoMock.getByTitleFuzzy(anyString())).thenAnswer(new Answer<List<BasicShop>>() {
			@Override
			public List<BasicShop> answer(InvocationOnMock invocation) throws Throwable {
				if (((String) invocation.getArguments()[0]).equals("test"))
					return list;
				return new LinkedList<BasicShop>();
			}
		});

		List<BasicShop> actual = _shopService.getByTitleFuzzy("test");

		for (BasicShop s : list)
			assertThat(actual, hasItem(s));
		assertThat(actual.size(), is(list.size()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getShopByTitleFuzzy_titleNull_shouldThrow() throws Exception {
		try {
			_shopService.getByTitleFuzzy(null);
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			verify(_shopDaoMock, never()).getByTitleFuzzy(anyString());
		}
	}



	@Test
	public void getAll_shouldReturnBasicShops() throws Exception {
		List<BasicShop> shopList = new LinkedList<BasicShop>();
		shopList.add(EntityCreator.createBasicShop(1L, "test1"));
		shopList.add(EntityCreator.createBasicShop(2L, "test2"));
		shopList.add(EntityCreator.createBasicShop(3L, "test3"));

		when(_shopDaoMock.getAll()).thenReturn(shopList);


		List<BasicShop> actual = _shopService.getAll();

		BasicShop shop1 = EntityCreator.createBasicShop(1L, "test1");
		BasicShop shop2 = EntityCreator.createBasicShop(2L, "test2");
		BasicShop shop3 = EntityCreator.createBasicShop(3L, "test3");

		assertThat(actual, hasItems(shop1, shop2, shop3));
		assertThat(actual.size(), is(3));
	}

}

package org.tagaprice.server.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@ContextConfiguration
public class DefaultShopServiceTest  extends AbstractJUnit4SpringContextTests {
	private Logger _log = LoggerFactory.getLogger(DefaultLoginService.class);
	private DefaultShopService _serviceManagement;
	private IShopDAO _serviceDAOMock;
	@Before
	public void setUp() {
		_serviceManagement = applicationContext.getBean("defaultShopManagement", DefaultShopService.class);
		_serviceDAOMock = mock(IShopDAO.class);
		_serviceManagement.setServiceDAO(_serviceDAOMock);
	}
	
	
	@Test
	public void getById_shouldReturnNull() throws Exception {
		_log.info("running test");
		
		Long id = 1L;
		when(_serviceDAOMock.getById(id)).thenReturn(null);
		
		Shop actual = _serviceManagement.getById(id);

		Shop expected = null;
		
		assertThat(actual, is(expected));
	}
	
	@Test
	public void getById_shouldReturnShop() throws Exception {
		_log.info("running test");
		
		Long id = 1L;
		when(_serviceDAOMock.getById(id)).thenReturn(EntityCreator.createShop(1L));
		
		Shop actual = _serviceManagement.getById(id);

		Shop expected = EntityCreator.createShop(id);
		
		assertThat(actual, is(expected));
	}
	
	@Test
	public void getByTitle_shouldReturnEmptyList() throws Exception {
		_log.info("running test");
		
		String id = "walmart";
		when(_serviceDAOMock.getByTitle(id)).thenReturn(new ArrayList<Shop>());
		
		List<Shop> actual = _serviceManagement.getByTitle(id);

		List<Shop> expected = new ArrayList<Shop>();
		
		assertThat(actual, is(expected));
	}
	
	@Test
	public void getByTitle_shouldReturn2Shops() throws Exception {
		_log.info("running test");
		
		String id = "walmart";
		when(_serviceDAOMock.getByTitle(id)).thenReturn(EntityCreator.createShops("walmart", "super walmart"));
		
		List<Shop> actual = _serviceManagement.getByTitle(id);

		List<Shop> expected = EntityCreator.createShops("walmart", "super walmart");
		
		assertThat(actual, is(expected));
	}
}

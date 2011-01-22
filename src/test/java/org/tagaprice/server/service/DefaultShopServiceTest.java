package org.tagaprice.server.service;


import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;



@ContextConfiguration
public class DefaultShopServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultShopService _shopService;
	private IShopDAO _shopDaoMock;


	@Before
	public void setUp() throws Exception {
		_shopService = applicationContext.getBean("defaultShopService", DefaultShopService.class);
		_shopDaoMock = mock(IShopDAO.class);
		_shopService.setShopDAO(_shopDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@Test
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		Shop toSave = EntityCreator.createShop(null);

		//Mock sets id and returns whatever it gets
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
		_shopService.save(null);
	}

}

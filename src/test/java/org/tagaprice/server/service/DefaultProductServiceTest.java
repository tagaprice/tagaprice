package org.tagaprice.server.service;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration
public class DefaultProductServiceTest  extends AbstractTransactionalJUnit4SpringContextTests {
	//	private DefaultProductService _productManagement;
	//	private IProductDAO _productDaoMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//		_productManagement = applicationContext.getBean("defaultProductManagement", DefaultProductService.class); //maybe replace this with autowire
		//		_productDaoMock = mock(IProductDAO.class);
		//		_productManagement.setProductDAO(_productDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() {
		//		Product productToSave = new Product().setId(null).setTitle("productTitle");
		//		Product expected = new Product().setId((long) 1).setTitle("productTitle");

		//		when(_productDaoMock.save(productToSave)).thenReturn(expected);
		//
		//
		//		Product actual = _productManagement.save(productToSave);
		//
		//		assertThat(actual, equalTo(expected));
	}
}

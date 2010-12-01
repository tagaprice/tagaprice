package org.tagaprice.server.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.tagaprice.core.beans.Product;
import org.tagaprice.server.dao.ints.IProductDAO;

public class DefaultProductServiceTest {
	DefaultProductService _productManagement;
	private IProductDAO _productDaoMock;
	static XmlBeanFactory _context;

	@BeforeClass
	public static void setUpBefore() throws Exception {
		ClassPathResource res = new ClassPathResource("test-beans.xml");
		DefaultProductServiceTest._context = new XmlBeanFactory(res);
	}

	@Before
	public void setUp() throws Exception {
		_productManagement = (DefaultProductService) DefaultProductServiceTest._context.getBean("standardProductManagement");
		_productDaoMock = mock(IProductDAO.class);
		_productManagement.setProductDAO(_productDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() {
		Product productToSave = new Product().setId(null).setTitle("productTitle");
		Product expected = new Product().setId((long) 1).setTitle("productTitle");

		when(_productDaoMock.save(productToSave)).thenReturn(expected);


		Product actual = _productManagement.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

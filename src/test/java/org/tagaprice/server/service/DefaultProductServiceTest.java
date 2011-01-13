package org.tagaprice.server.service;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.dao.helper.EntityCreator;
import org.tagaprice.server.dao.interfaces.IProductDAO;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;



@ContextConfiguration
public class DefaultProductServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultProductService _productManagement;
	private IProductDAO _productDaoMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_productManagement = applicationContext.getBean("defaultProductManagement", DefaultProductService.class); //maybe replace this with autowire
		_productDaoMock = mock(IProductDAO.class);
		_productManagement.setProductDAO(_productDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		Product toSave = EntityCreator.createProductWithRevisions(null, 1);

		//Mock returns whatever it gets
		when(_productDaoMock.save((Product) any())).thenAnswer(new Answer<Product>() {
			@Override
			public Product answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Product) args[0];
			}

		});

		Product actual = _productManagement.save(toSave);

		Product expected = EntityCreator.createProductWithRevisions(1L, 1);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItems(expected.getCurrentRevision()));
	}

	@Test
	public void saveProductWithNewRevision_shouldReturnProductWithAllRevisions() throws Exception {

	}

	@Test
	public void saveProductWithOutdatedRevision_shouldThrowException() throws Exception {

	}

	@Test
	public void saveProductWithOutChanges_shouldReturnProductAsItIs() throws Exception {

	}
}

package org.tagaprice.server.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;



@ContextConfiguration
public class DefaultProductServiceTest  extends AbstractJUnit4SpringContextTests {
	private DefaultProductService _productManagement;
	private IProductDAO _productDaoMock;
	private IProductRevisionDAO _productRevisionDaoMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_productManagement = applicationContext.getBean("defaultProductManagement", DefaultProductService.class); //maybe replace this with autowire
		_productDaoMock = mock(IProductDAO.class);
		_productRevisionDaoMock = mock(IProductRevisionDAO.class);
		_productManagement.setProductDAO(_productDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
		_productManagement.setProductRevisionDAO(_productRevisionDaoMock); //TODO replace this by dependency injection via autowire, how to inject mock ? see http://stackoverflow.com/questions/2457239/injecting-mockito-mocks-into-a-spring-bean for help
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
	public void saveAlreadyPersistedProductWithNewRevision_shouldReturnProductWithAllRevisions() throws Exception {
		Long id = 1L;
		ProductRevision productRevisionToSave = EntityCreator.createProductRevision(id, 3);
		Product toSave = EntityCreator.createProductWithRevisions(id, productRevisionToSave);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);

		Product totalProduct = EntityCreator.createProductWithRevisions(id, 3);
		when(_productDaoMock.save((Product) any())).thenReturn(totalProduct);

		Product actual = _productManagement.save(toSave);

		Product expected = EntityCreator.createProductWithRevisions(id, 3);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions().size(), equalTo(expected.getRevisions().size()));
		for(ProductRevision expectedRevision : expected.getRevisions()) {
			assertThat(actual.getRevisions(), hasItems(expectedRevision));
		}
	}

	@Test(expected=OutdatedRevisionException.class)
	public void saveAlreadyPersistedProductWithOutdatedRevision_shouldThrowException() throws Exception {
		Long id = 1L;
		HashSet<ProductRevision> revisions = new HashSet<ProductRevision>();
		ProductRevision baseRevision = EntityCreator.createProductRevision(id, 1);
		revisions.add(baseRevision);
		ProductRevision newRevision = EntityCreator.createProductRevision(id, 2, "other title");
		revisions.add(newRevision);
		Product toSave = EntityCreator.createProductWithRevisions(id, revisions);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);

		_productManagement.save(toSave);
	}

	@Test
	public void saveAlreadyPersistedProductWithOutChanges_shouldReturnProductAsItIs() throws Exception {
		Long id = 1L;
		Product toSave = EntityCreator.createProductWithRevisions(id, 2);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);
		when(_productDaoMock.save((Product) any())).thenReturn(alreadyPersistedProduct);

		Product expected = EntityCreator.createProductWithRevisions(id, 2);

		Product actual = _productManagement.save(toSave);

		assertThat(actual, equalTo(expected));
	}

	@Test
	public void getByTitle_shouldReturn2ProductsWithAtLeastOneRevisionHavingTitleCoke() throws Exception {
		String title = "coke";
		long id_1 = 1L;
		long id_2 = 2L;

		ProductRevision productRevision_1_1 = EntityCreator.createProductRevision(id_1, 1, "other "+title);
		ProductRevision productRevision_1_2 = EntityCreator.createProductRevision(id_1, 2, "new "+title);
		ProductRevision productRevision_2_1 = EntityCreator.createProductRevision(id_2, 1, title);

		ArrayList<ProductRevision> revisionsContainingTitle = new ArrayList<ProductRevision>();
		revisionsContainingTitle.add(productRevision_1_1);
		revisionsContainingTitle.add(productRevision_1_2);
		revisionsContainingTitle.add(productRevision_2_1);

		when(_productRevisionDaoMock.getByTitle(title)).thenReturn(revisionsContainingTitle);

		Product productContainingTitle_1 = EntityCreator.createProductWithRevisions(id_1, productRevision_1_1, productRevision_1_2);
		ProductRevision productRevision_2_2_NotContainingTitle = EntityCreator.createProductRevision(id_2, 2, "nope");
		Product productContainingTitle_2 = EntityCreator.createProductWithRevisions(id_2, productRevision_2_2_NotContainingTitle, productRevision_2_1);

		when(_productDaoMock.getById(id_1)).thenReturn(productContainingTitle_1);
		when(_productDaoMock.getById(id_2)).thenReturn(productContainingTitle_2);

		List<Product> actual = _productManagement.getByTitle(title);

		ProductRevision expected_revision_1_1 = EntityCreator.createProductRevision(id_1, 1, "other "+title);
		ProductRevision expected_revision_1_2 = EntityCreator.createProductRevision(id_1, 2, "new "+title);
		ProductRevision expected_revision_2_1 = EntityCreator.createProductRevision(id_2, 1, title);
		ProductRevision expected_revision_2_2 = EntityCreator.createProductRevision(id_2, 2, "nope");

		Product expected_product_1 = EntityCreator.createProductWithRevisions(id_1, expected_revision_1_1, expected_revision_1_2);
		Product expected_product_2 = EntityCreator.createProductWithRevisions(id_2, expected_revision_2_1, expected_revision_2_2);

		List<Product> expected = new ArrayList<Product>();
		expected.add(expected_product_1);
		expected.add(expected_product_2);


		compare(actual, expected);
	}

	private static void compare(List<Product> actual, List<Product> expected) {
		assertThat(actual, is(expected));

		Iterator<Product> itActual = actual.iterator();
		Iterator<Product> itExpected = expected.iterator();
		while(itActual.hasNext()) {
			Product curActual = itActual.next();
			Product curExpected = itExpected.next();

			assertThat(curActual.getRevisions(), is(curExpected.getRevisions()));
		}
	}

	@Test
	public void getByTitle_shouldReturnEmptyList() throws Exception {
		String title = "no title";

		ArrayList<ProductRevision> revisionsContainingTitle = new ArrayList<ProductRevision>();
		when(_productRevisionDaoMock.getByTitle(title)).thenReturn(revisionsContainingTitle);

		List<Product> actual = _productManagement.getByTitle(title);

		List<Product> expected = new ArrayList<Product>();

		assertThat(actual, is(expected));
	}
}

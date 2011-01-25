package org.tagaprice.server.service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;
import org.tagaprice.server.service.helper.EntityCreator;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;



@ContextConfiguration
public class DefaultProductServiceTest  extends AbstractJUnit4SpringContextTests {

	private static Logger _log = LoggerFactory.getLogger(DefaultProductServiceTest.class);

	private DefaultProductService _productManagement;
	private IProductDAO _productDaoMock;
	private IProductRevisionDAO _productRevisionDaoMock;
	private SessionService _sessionFactoryMock;

	@BeforeClass
	public static void setUpBefore() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_productManagement = applicationContext.getBean("defaultProductManagement", DefaultProductService.class); //maybe replace this with autowire
		
		_productDaoMock = mock(IProductDAO.class);
		_productRevisionDaoMock = mock(IProductRevisionDAO.class);
		_sessionFactoryMock = mock(SessionService.class);
		
		_productManagement.setProductDAO(_productDaoMock);
		_productManagement.setProductRevisionDAO(_productRevisionDaoMock);
		_productManagement.setSessionFactory(_sessionFactoryMock);
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		Product toSave = EntityCreator.createProductWithRevisions(null, 1);

		//Mock returns whatever it gets
		when(_productDaoMock.save((Product) any())).thenAnswer(new Answer<Product>() {
			@Override
			public Product answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Product product = (Product) args[0];
				product.setId(4L);
				return product; 
			}

		});
		
		when(_sessionFactoryMock.getAccount((Session) any())).thenReturn(EntityCreator.createAccount());
		

		Product actual = _productManagement.save(toSave, EntityCreator.createSession());

		Product expected = EntityCreator.createProductWithRevisions(4L, 1);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItems(expected.getCurrentRevision()));
	}

	@Test
	public void saveAlreadyPersistedProductWithNewRevision_shouldReturnProductWithAllRevisions() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		Long id = 1L;
		ProductRevision productRevisionToSave = EntityCreator.createProductRevision(id, 3);
		Product toSave = EntityCreator.createProductWithRevisions(id, productRevisionToSave);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);

		Product totalProduct = EntityCreator.createProductWithRevisions(id, 3);
		when(_productDaoMock.save((Product) any())).thenReturn(totalProduct);
		
		when(_sessionFactoryMock.getAccount((Session) any())).thenReturn(EntityCreator.createAccount());

		Product actual = _productManagement.save(toSave, EntityCreator.createSession());

		Product expected = EntityCreator.createProductWithRevisions(id, 3);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions().size(), equalTo(expected.getRevisions().size()));
		for(ProductRevision expectedRevision : expected.getRevisions()) {
			assertThat(actual.getRevisions(), hasItems(expectedRevision));
		}
	}

	@Test(expected=OutdatedRevisionException.class)
	public void saveAlreadyPersistedProductWithOutdatedRevision_shouldThrowException() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		Long id = 1L;
		HashSet<ProductRevision> revisions = new HashSet<ProductRevision>();
		ProductRevision baseRevision = EntityCreator.createProductRevision(id, 1);
		revisions.add(baseRevision);
		ProductRevision newRevision = EntityCreator.createProductRevision(id, 2, "other title");
		revisions.add(newRevision);
		Product toSave = EntityCreator.createProductWithRevisions(id, revisions);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);
		
		when(_sessionFactoryMock.getAccount((Session) any())).thenReturn(EntityCreator.createAccount());

		_productManagement.save(toSave, EntityCreator.createSession());
	}

	@Test
	public void saveAlreadyPersistedProductWithOutChanges_shouldReturnProductAsItIs() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		Long id = 1L;
		Product toSave = EntityCreator.createProductWithRevisions(id, 2);

		Product alreadyPersistedProduct = EntityCreator.createProductWithRevisions(id, 2);
		when(_productDaoMock.getById(id)).thenReturn(alreadyPersistedProduct);
		when(_productDaoMock.save((Product) any())).thenReturn(alreadyPersistedProduct);
		
		when(_sessionFactoryMock.getAccount((Session) any())).thenReturn(EntityCreator.createAccount());

		Product expected = EntityCreator.createProductWithRevisions(id, 2);

		Product actual = _productManagement.save(toSave, EntityCreator.createSession());

		assertThat(actual, equalTo(expected));
	}
	
	@Test(expected=UserNotLoggedInException.class)
	public void saveProduct_passInvalidSessionToken_ShouldThrowUserNotLoggedInException() throws UserNotLoggedInException, OutdatedRevisionException {
		DefaultProductServiceTest._log.info("running test");

		Product toSave = EntityCreator.createProductWithRevisions(null, 1);

		when(_sessionFactoryMock.getAccount((Session) any())).thenReturn(null);
		
		_productManagement.save(toSave, EntityCreator.createSession());
	}

	@Test
	public void getByTitle_shouldReturn2ProductsWithAtLeastOneRevisionHavingTitleCoke() throws Exception {
		DefaultProductServiceTest._log.info("running test");

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
		DefaultProductServiceTest._log.info("running test");

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
		DefaultProductServiceTest._log.info("running test");

		String title = "no title";

		ArrayList<ProductRevision> revisionsContainingTitle = new ArrayList<ProductRevision>();
		when(_productRevisionDaoMock.getByTitle(title)).thenReturn(revisionsContainingTitle);

		List<Product> actual = _productManagement.getByTitle(title);

		List<Product> expected = new ArrayList<Product>();

		assertThat(actual, is(expected));
	}

	@Test
	public void getAll_shouldGetProductList() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		List<Product> products = new LinkedList<Product>();
		products.add(EntityCreator.createProductWithRevisions(1L, 2));
		products.add(EntityCreator.createProductWithRevisions(2L, 4));
		products.add(EntityCreator.createProductWithRevisions(5L, 1));

		when(_productDaoMock.getAll()).thenReturn(products );

		List<Product> actual = _productManagement.getAll();

		assertThat(actual, is(products));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getById_idNull_shouldThrow() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		try {
			_productManagement.getById(null);
		} catch (IllegalArgumentException e) {
			throw e;
		} finally {
			verify(_productDaoMock, never()).getById(anyLong());
		}
	}

	@Test
	public void getById_shouldGetProduct() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		when(_productDaoMock.getById(1L)).thenReturn(EntityCreator.createProductWithRevisions(1L, 1));

		Product actual = _productManagement.getById(1L);

		Product expected = EntityCreator.createProductWithRevisions(1L, 1);

		assertThat(actual, is(expected));
	}


	@Test
	public void getById_unknownId_shouldReturnNull() throws Exception {
		DefaultProductServiceTest._log.info("running test");

		when(_productDaoMock.getById(1L)).thenReturn(null);

		Product actual = _productManagement.getById(1L);

		assertThat(actual, equalTo(null));
	}
}

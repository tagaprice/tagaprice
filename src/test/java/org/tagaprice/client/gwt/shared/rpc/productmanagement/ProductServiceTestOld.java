package org.tagaprice.client.gwt.shared.rpc.productmanagement;


import org.junit.*;
import org.mockito.Mockito;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

public class ProductServiceTestOld {

	static IProductService productService;
	static IProduct initialProduct;
	static RevisionId initialRevisionId;
	static IProduct updatedProduct;
	static RevisionId updatedRevisionId;

	static IProduct myTestProduct;
	static RevisionId myTestProductRevisionId;
	static IProduct myUpdatedTestProduct;
	static RevisionId myUpdatedTestProductRevisionId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// these objects are mocked...
		/*
		 * Create 2 Products that are initially saved into the ProductService
		 */

		/*
		 * A first Mock that imitates a new Product with the special productId = 0 and revisionId = 0.
		 */
		ProductServiceTestOld.initialProduct = Mockito.mock(IProduct.class);
		ProductServiceTestOld.initialRevisionId = Mockito.mock(RevisionId.class);
		// Add responses
		Mockito.when(ProductServiceTestOld.initialRevisionId.getId()).thenReturn(0L);
		Mockito.when(ProductServiceTestOld.initialRevisionId.getRevision()).thenReturn(0L);
		Mockito.when(ProductServiceTestOld.initialProduct.getRevisionId())
		.thenReturn(ProductServiceTestOld.initialRevisionId);
		Mockito.when(ProductServiceTestOld.initialProduct.getTitle()).thenReturn("TestTitle");
		// Add expectations

		/*
		 * A second Mock that imitates the first product after the initial save.
		 * We assumed, that the productId of the new product is 1.
		 */
		ProductServiceTestOld.updatedProduct = Mockito.mock(IProduct.class);
		ProductServiceTestOld.updatedRevisionId = Mockito.mock(RevisionId.class);
		// Add responses
		Mockito.when(ProductServiceTestOld.updatedRevisionId.getId()).thenReturn(1L);
		Mockito.when(ProductServiceTestOld.updatedRevisionId.getRevision()).thenReturn(1L);
		Mockito.when(ProductServiceTestOld.updatedProduct.getRevisionId())
		.thenReturn(ProductServiceTestOld.updatedRevisionId);
		Mockito.when(ProductServiceTestOld.updatedProduct.getTitle()).thenReturn("NewTestTitle");

		ProductServiceTestOld.myTestProduct = Mockito.mock(IProduct.class);
		//The initial Revision is always the same
		ProductServiceTestOld.myTestProductRevisionId = ProductServiceTestOld.initialRevisionId;

		Mockito.when(ProductServiceTestOld.myTestProduct.getTitle()).thenReturn("TheNewestProduct");
		Mockito.when(ProductServiceTestOld.myTestProduct.getRevisionId()).thenReturn(ProductServiceTestOld.myTestProductRevisionId);

		ProductServiceTestOld.myUpdatedTestProduct = Mockito.mock(IProduct.class);
		ProductServiceTestOld.myUpdatedTestProductRevisionId = Mockito.mock(RevisionId.class);
		Mockito.when(ProductServiceTestOld.myTestProduct.getTitle()).thenReturn("TheSecondNewestProduct");
		Mockito.when(ProductServiceTestOld.myUpdatedTestProduct.getRevisionId()).thenReturn(ProductServiceTestOld.myUpdatedTestProductRevisionId);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// class under test... must be replaced by implementation
		ProductServiceTestOld.productService = Mockito.mock(IProductService.class);

		/*
		 * We store the two Objects into the productService.
		 */
		ProductServiceTestOld.productService.saveProduct(ProductServiceTestOld.initialProduct);
		ProductServiceTestOld.productService.saveProduct(ProductServiceTestOld.updatedProduct);
		/*
		 * Now we have 1 product saved with 2 revisions in the ProductService.
		 */
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * TESTCASE: A new product is saved.
	 * A product with id=0 gets a new id and an initial revisionId.
	 * The first product will get id=1, and RevisionId=1.
	 * Productname
	 */
	@Test
	public void saveNewProduct_shouldReturnNewProduct() {

		IProduct mySavedProduct = ProductServiceTestOld.productService.saveProduct(ProductServiceTestOld.myTestProduct);
		// We assume that the new productId is 2 because we already have stored one product (id 1 is used).
		Assert.assertTrue(2L == mySavedProduct.getRevisionId().getId());
		Assert.assertTrue(1L == mySavedProduct.getRevisionId().getRevision());

	}

	/**
	 * TESTCASE: An existing product is updated.
	 * UPDATE: the existing product gets a new revisionId incremented by one.
	 */
	@Test
	public void saveAndUpdateProduct_shouldReturnNewRevision() {
		ProductServiceTestOld.productService.saveProduct(ProductServiceTestOld.myTestProduct);
		IProduct mySavedProduct = ProductServiceTestOld.productService.saveProduct(ProductServiceTestOld.myUpdatedTestProduct);

		Assert.assertTrue(2L == mySavedProduct.getRevisionId().getId());
		Assert.assertTrue(2L == mySavedProduct.getRevisionId().getRevision());

	}

	/**
	 * A product with an older RevisionID than the last one can not be saved.
	 */
	@Test
	public void saveAndUpdateProduct_shouldThrowException() {

	}



}

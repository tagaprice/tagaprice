package org.tagaprice.client.gwt.shared.rpc.productmanagement;


import org.junit.*;
import org.mockito.Mockito;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;

public class ProductServiceTest {

	static IProductService productService;
	static IProduct initialProduct;
	static RevisionId initialRevisionId;
	static IProduct updatedProduct;
	static RevisionId updatedRevisionId;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//class under test... must be replaced by implementation
		ProductServiceTest.productService = Mockito.mock(IProductService.class);

		//these objects are mocked...
		ProductServiceTest.initialProduct = Mockito.mock(IProduct.class);
		//Add responses
		Mockito.when(ProductServiceTest.initialRevisionId.getId()).thenReturn(0L);
		Mockito.when(ProductServiceTest.initialRevisionId.getRevision()).thenReturn(0L);
		Mockito.when(ProductServiceTest.initialProduct.getRevisionId()).thenReturn(ProductServiceTest.initialRevisionId);
		Mockito.when(ProductServiceTest.initialProduct.getTitle()).thenReturn("TestTitle");
		//Add expectations
		ProductServiceTest.updatedProduct = Mockito.mock(IProduct.class);
		//Add responses

		//We assume this!!!
		Mockito.when(ProductServiceTest.updatedRevisionId.getId()).thenReturn(1L);
		Mockito.when(ProductServiceTest.updatedRevisionId.getRevision()).thenReturn(1L);
		Mockito.when(ProductServiceTest.updatedProduct.getRevisionId()).thenReturn(ProductServiceTest.updatedRevisionId);
		Mockito.when(ProductServiceTest.updatedProduct.getTitle()).thenReturn("NewTestTitle");
		//Add expectations

		ProductServiceTest.productService.saveProduct(ProductServiceTest.initialProduct);
		ProductServiceTest.productService.saveProduct(ProductServiceTest.updatedProduct);

		//Now we have 1 product saved with 2 revisions

	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * A product with id=0 get's a new id and a initial revisionId.
	 * The first product will get id=1, and RevisionId=1.
	 * Productname
	 */
	@Test
	public void saveNewProduct_shouldReturnNewProduct() {
		IProduct myTestProduct = Mockito.mock(IProduct.class);
		IProduct mySavedProduct = ProductServiceTest.productService.saveProduct(myTestProduct);

		Assert.assertTrue(2L == mySavedProduct.getRevisionId().getId());
		Assert.assertTrue(2L == mySavedProduct.getRevisionId().getRevision());

	}

	/**
	 * Updating an existing product saves the product and returns a new Revision for that product
	 */
	@Test
	public void saveAndUpdateProduct_shouldReturnNewRevision() {
		ProductServiceTest.updatedProduct.setTitle("TestProduct2");
		ProductServiceTest.productService.saveProduct(ProductServiceTest.updatedProduct);

		Assert.assertTrue(3L == ProductServiceTest.updatedProduct.getRevisionId().getRevision());

	}

	/**
	 * A product with an older RevisionID than the last one can not be saved.
	 */
	@Test
	public void saveAndUpdateProduct_shouldThrowException() {

	}





}

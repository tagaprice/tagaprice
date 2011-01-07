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
		ProductServiceTest.productService = Mockito.mock(IProductService.class);
		ProductServiceTest.initialProduct = Mockito.mock(IProduct.class);
		//Add responses
		Mockito.when(ProductServiceTest.initialRevisionId.getId()).thenReturn(0L);
		Mockito.when(ProductServiceTest.initialRevisionId.getRevision()).thenReturn(0L);
		Mockito.when(ProductServiceTest.initialProduct.getRevisionId()).thenReturn(ProductServiceTest.initialRevisionId);
		Mockito.when(ProductServiceTest.initialProduct.getTitle()).thenReturn("TestTitle");
		//Add expectations
		ProductServiceTest.updatedProduct = Mockito.mock(IProduct.class);
		//Add responses
		//Add expectations
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
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

	}

	/**
	 * A product with
	 */
	@Test
	public void saveAndUpdateProduct_shouldReturnNewRevision() {

	}

	/**
	 * A product with an older RevisionID than the last one can not be saved.
	 */
	@Test
	public void saveAndUpdateProduct_shouldThrowException() {

	}





}

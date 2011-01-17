package org.tagaprice.client.gwt.server.mock;


import org.junit.*;
import org.mockito.Mockito;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;

/**
 * This class tests the mocked (without persistence) ProductService.
 * It relies on the Products that are "delivered" with the ProductService.
 * 
 * @author Martin
 * 
 */
public class ProductServiceImplTest {
	ProductServiceImpl service;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.service = new ProductServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProductByIdShoulReturnProduct() {
		IRevisionId rev = Mockito.mock(IRevisionId.class);
		//Product with id = 1
		Mockito.when(rev.getId()).thenReturn(1L);
		//Rev 0 -> latest revision
		Mockito.when(rev.getRevision()).thenReturn(0L);
		rev = new RevisionId(1L);
		IProduct product = this.service.getProduct(rev);
		Assert.assertNotNull(product);
		Assert.assertEquals(1L, product.getRevisionId().getId().longValue());
	}

	@Test
	public void saveNewProductShouldReturnCopyOfProduct() {
		String productname = "testproduct";
		ICategory productcategory = new Category("testcat");
		IQuantity productquantity = new Quantity(1.2, Unit.kg);
		Product product = new Product(productname, productcategory, productquantity);
		IProduct savedProduct = this.service.saveProduct(product);
		Assert.assertEquals(1L, savedProduct.getRevisionId().getRevision().longValue());
		Assert.assertEquals(productname, savedProduct.getTitle());
		Assert.assertEquals(productcategory, savedProduct.getCategory());
		Assert.assertEquals(productquantity, savedProduct.getQuantity());
	}

}

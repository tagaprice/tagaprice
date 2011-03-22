package org.tagaprice.client.gwt.server.mock;


import org.junit.*;
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
		IRevisionId rev = new RevisionId(1L);
		IProduct product = this.service.getProduct(rev);
		Assert.assertNotNull(product);
		Assert.assertEquals(1L, product.getRevisionId().getId());
	}

	@Test
	public void testSaveNewProductShouldReturnCopyOfProduct() {
		String productname = "testproduct";
		ICategory productcategory = new Category("testcat");
		IQuantity productquantity = new Quantity(1.2, Unit.kg);
		Product product = new Product(productname, productcategory, productquantity);
		IProduct savedProduct = this.service.saveProduct(product);
		Assert.assertEquals(1L, savedProduct.getRevisionId().getRevision());
		Assert.assertEquals(productname, savedProduct.getTitle());
		Assert.assertEquals(productcategory, savedProduct.getCategory());
		Assert.assertEquals(productquantity, savedProduct.getQuantity());
	}

	@Test
	public void testGetProductAndUpdate() {
		IRevisionId rev = new RevisionId(1L);
		//This should return two different objects
		IProduct originalProduct = this.service.getProduct(rev);
		IProduct changedProduct = this.service.getProduct(rev);
		Assert.assertTrue(originalProduct != changedProduct);
		Assert.assertEquals("The 2 Objects should be equal, maybe You equals is not implemented correctly?", originalProduct, changedProduct);
		changedProduct.setTitle("newTitle");

		//save changed product and check
		IProduct savedProduct = this.service.saveProduct(changedProduct);
		Assert.assertEquals(2L, savedProduct.getRevisionId().getRevision());

		//Check, if saving operation succeded
		//Get latest revision
		IProduct shouldBeSavedProduct = this.service.getProduct(rev);
		IProduct shouldBeSavedProductWithRevision = this.service.getProduct(new RevisionId(1L, 2));
		IProduct shouldBeOriginalProduct = this.service.getProduct(new RevisionId(1L, 1));

		Assert.assertEquals(savedProduct, shouldBeSavedProduct);
		Assert.assertEquals(savedProduct, shouldBeSavedProductWithRevision);
		Assert.assertEquals(originalProduct, shouldBeOriginalProduct);
	}

}

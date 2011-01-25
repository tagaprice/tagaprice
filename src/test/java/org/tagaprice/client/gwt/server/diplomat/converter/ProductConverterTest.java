package org.tagaprice.client.gwt.server.diplomat.converter;


import org.junit.*;
import org.tagaprice.client.gwt.server.diplomat.converter.ProductConverter;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.entities.*;

public class ProductConverterTest {
	ProductConverter productConverter = new ProductConverter();
	Product newProductCore;
	IProduct newProductGWT;
	Product changedProductCore;
	IProduct changedProductGWT;

	CategoryConverterTest categories = new CategoryConverterTest();

	String newProductTitle = "newProduct";
	Unit newProductUnit = Unit.kg;
	Double newProductAmount = 2.3;
	String newProductImageURL = "";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		categories.setUp();
		//Setup new Products
		ProductRevision newProductRevision = new ProductRevision(null, 1, newProductTitle, DefaultValues.defaultDate , DefaultValues.defaultCoreAccount, newProductUnit, newProductAmount,categories.coreCategoryChild , newProductImageURL);
		java.util.HashSet<ProductRevision> revisions = new java.util.HashSet<ProductRevision>();
		revisions.add(newProductRevision);
		this.newProductCore = new Product(null, DefaultValues.defaultCoreLocale, revisions);

		this.newProductGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(newProductTitle, categories.gwtCategoryChild, new Quantity(newProductAmount, newProductUnit));
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests, if a new Product in GWT is correctly converted to a Product in core.
	 * A new Product in core has id = null and revision = 1, in gwt it can be zero or null also.
	 * @throws Exception
	 */
	@Test
	public void testConvertNewGWTProductToCore() throws Exception {
		Product productCore = productConverter.convertProductToCore(this.newProductGWT);
		ProductRevision revisionCore = productCore.getCurrentRevision();

		Assert.assertEquals(null, revisionCore.getId());
		Assert.assertEquals(null, productCore.getId());
		Assert.assertEquals(1, revisionCore.getRevisionNumber().intValue());
		Assert.assertEquals(this.newProductTitle, revisionCore.getTitle());
		Assert.assertEquals(this.newProductAmount.doubleValue(), revisionCore.getAmount().doubleValue(), 0.001);
		Assert.assertEquals(this.newProductUnit, revisionCore.getUnit());
		Assert.assertEquals(this.categories.coreCategoryChild, revisionCore.getCategory());
		Assert.assertEquals(this.newProductCore, productCore);
	}
	@Test
	public void testConvertExistingGWTProductToCore() throws Exception {
		Assert.fail("not yet implemented");
	}

}

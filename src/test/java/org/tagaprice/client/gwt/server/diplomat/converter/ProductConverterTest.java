package org.tagaprice.client.gwt.server.diplomat.converter;


import java.util.Date;

import org.junit.*;
import org.tagaprice.client.gwt.server.diplomat.converter.ProductConverter;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.entities.*;
import org.tagaprice.server.service.helper.EntityCreator;

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
		ProductRevision newProductRevision = new ProductRevision(null, null, newProductTitle, new Date(), ProductConverter.defaultCoreAccount, newProductUnit, newProductAmount,categories.coreCategoryChild , newProductImageURL);
		java.util.HashSet<ProductRevision> revisions = new java.util.HashSet<ProductRevision>();
		revisions.add(newProductRevision);
		this.newProductCore = new Product(null, ProductConverter.defaultCoreLocale, revisions);

		this.newProductGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(newProductTitle, categories.gwtCategoryChild, new Quantity(newProductAmount, newProductUnit));
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests, if a new Product in GWT is corrctly converted to a Product in core.
	 * A new Product in core has id = null and revision = 1, in gwt it can be zero or null also.
	 * @throws Exception
	 */
	@Test
	public void testConvertNewProductToCore() throws Exception {
		Product productCore = productConverter.convertProductToCore(this.newProductGWT);
		ProductRevision revisionCore = productCore.getCurrentRevision();

		Assert.assertEquals(null, revisionCore.getId());
		Assert.assertEquals(null, productCore.getId());
		Assert.assertEquals(1, revisionCore.getRevisionNumber().intValue());
		Assert.assertEquals(this.newProductTitle, revisionCore.getTitle());
		Assert.assertEquals(this.newProductAmount.doubleValue(), revisionCore.getAmount().doubleValue(), 0.001);
		Assert.assertEquals(this.newProductUnit, revisionCore.getUnit());
		Assert.assertEquals(this.categories.coreCategoryChild, revisionCore.getCategory());
	}
	@Test
	public void testConvertExistingProductToCore() throws Exception {
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product("Testprodukt", new org.tagaprice.client.gwt.shared.entities.dump.Category("Testcategory"), new Quantity(3.4,Unit.kg));
		productGWT.setRevisionId(new RevisionId(3L, 3L));

		Product productCore = productConverter.convertProductToCore(productGWT);
		Assert.assertEquals(productGWT.getRevisionId().getId(), productCore.getId().longValue());
		Assert.assertEquals(productGWT.getRevisionId().getRevision(), productCore.getCurrentRevision().getRevisionNumber().intValue());
		Assert.assertEquals(productGWT.getTitle(), productCore.getCurrentRevision().getTitle());
		Assert.assertEquals(productGWT.getCategory().getTitle(), productCore.getCurrentRevision().getCategory().getTitle());
		Assert.assertEquals(productGWT.getQuantity().getQuantity(), productCore.getCurrentRevision().getAmount().doubleValue(), 0.01);
		Assert.assertEquals(productGWT.getQuantity().getUnit(), productCore.getCurrentRevision().getUnit());
	}

	@Test
	public void testConvertProductToGWT() throws Exception {

		Product productCore = EntityCreator.createProductWithRevisions(3L, 2, 1, new Category(1L, "TestCat", null, new Date(), null));

		IProduct productGWT = productConverter.convertProductToGWT(productCore, 0);

		Assert.assertEquals(productCore.getCurrentRevision().getTitle(), productGWT.getTitle());
		Assert.assertEquals(productCore.getId().longValue(), productGWT.getRevisionId().getId());
		Assert.assertEquals(productCore.getCurrentRevision().getRevisionNumber().intValue(), new Long(productGWT.getRevisionId().getRevision()).intValue());
		Assert.assertEquals(productCore.getCurrentRevision().getCategory().getTitle(), productGWT.getCategory().getTitle());
		Assert.assertEquals(productCore.getCurrentRevision().getAmount().doubleValue(), productGWT.getQuantity().getQuantity(), 0.01);
		Assert.assertEquals(productCore.getCurrentRevision().getUnit(), productGWT.getQuantity().getUnit());
	}

}

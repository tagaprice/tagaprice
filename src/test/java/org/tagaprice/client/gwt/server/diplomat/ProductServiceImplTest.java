package org.tagaprice.client.gwt.server.diplomat;


import java.util.Date;

import org.junit.*;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.entities.*;
import org.tagaprice.server.service.helper.EntityCreator;

public class ProductServiceImplTest {
	ProductServiceImpl productService = new ProductServiceImpl();
	Product newProductCore;
	IProduct newProductGWT;
	Product changedProductCore;
	IProduct changedProductGWT;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		//Setup new Products
		//this.newProductCore = new Product(id, locale, revisions)
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvertProductToCore() throws Exception {
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product("Testprodukt", new org.tagaprice.client.gwt.shared.entities.dump.Category("Testcategory"), new Quantity(3.4,Unit.kg));
		productGWT.setRevisionId(new RevisionId(3L, 2L));

		Product productCore = productService.convertProductToCore(productGWT);
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

		IProduct productGWT = productService.convertProductToGWT(productCore, 0);

		Assert.assertEquals(productCore.getCurrentRevision().getTitle(), productGWT.getTitle());
		Assert.assertEquals(productCore.getId().longValue(), productGWT.getRevisionId().getId());
		Assert.assertEquals(productCore.getCurrentRevision().getRevisionNumber().intValue(), new Long(productGWT.getRevisionId().getRevision()).intValue());
		Assert.assertEquals(productCore.getCurrentRevision().getCategory().getTitle(), productGWT.getCategory().getTitle());
		Assert.assertEquals(productCore.getCurrentRevision().getAmount().doubleValue(), productGWT.getQuantity().getQuantity(), 0.01);
		Assert.assertEquals(productCore.getCurrentRevision().getUnit(), productGWT.getQuantity().getUnit());
	}

}

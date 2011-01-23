package org.tagaprice.client.gwt.server.diplomat;


import java.util.Date;

import org.junit.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.core.entities.*;
import org.tagaprice.server.service.helper.EntityCreator;

public class ProductServiceImplTest {
	ProductServiceImpl productService = new ProductServiceImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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

	@Test
	public void testConvertProductToCore() throws Exception {
		Assert.fail("not implemented");
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

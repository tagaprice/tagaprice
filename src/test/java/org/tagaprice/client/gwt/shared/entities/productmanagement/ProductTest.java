package org.tagaprice.client.gwt.shared.entities.productmanagement;


import org.junit.*;
import org.mockito.Mockito;

public class ProductTest {
	IProduct _product;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this._product = Mockito.mock(IProduct.class);
	}

	@After
	public void tearDown() throws Exception {
	}

}

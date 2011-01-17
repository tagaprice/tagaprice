package org.tagaprice.client.gwt.shared.entities.dump;


import org.junit.*;

public class CategoryTest {
	ICategory root = new Category("root");
	ICategory food = new Category("food");
	ICategory vegetables = new Category("vegetables");
	ICategory beverages = new Category("beverages");
	ICategory alcoholics = new Category("alcohol");
	ICategory nonalcoholics = new Category("nonalcoholics");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		root.addChildCategory(food);
		root.addChildCategory(beverages);
		food.addChildCategory(vegetables);
		beverages.addChildCategory(alcoholics);
		beverages.addChildCategory(nonalcoholics);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString_onlyNormalCases() {
		Assert.assertEquals("root", root.toString());
		Assert.assertEquals("root->food", food.toString());
		Assert.assertEquals("root->food->vegetables", vegetables.toString());
	}



}

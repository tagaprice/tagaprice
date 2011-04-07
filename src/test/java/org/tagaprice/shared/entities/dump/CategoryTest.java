package org.tagaprice.shared.entities.dump;


import org.junit.*;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.dump.ICategory;

public class CategoryTest {
	ICategory root = new Category("root", null);
	ICategory food = new Category("food", root);
	ICategory vegetables = new Category("vegetables", food);
	ICategory beverages = new Category("beverages", food);
	ICategory alcoholics = new Category("alcohol", beverages);
	ICategory nonalcoholics = new Category("nonalcoholics", beverages);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		nonalcoholics.setParentCategory(beverages);
		alcoholics.setParentCategory(beverages);
		vegetables.setParentCategory(food);
		beverages.setParentCategory(root);
		food.setParentCategory(root);
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

	@Test
	public void testEquals() {

	}
}

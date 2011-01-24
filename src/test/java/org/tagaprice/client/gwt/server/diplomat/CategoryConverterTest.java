package org.tagaprice.client.gwt.server.diplomat;


import java.util.Date;

import org.junit.*;
import org.tagaprice.client.gwt.server.diplomat.converter.CategoryConverter;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.core.entities.Category;

public class CategoryConverterTest {
	private CategoryConverter converter = CategoryConverter.getInstance();

	public Category coreCategoryRoot;
	public Category coreCategoryChild;
	public ICategory gwtCategoryRoot;
	public ICategory gwtCategoryChild;

	public String rootTitle = "root";
	public long rootId = 2L;
	public String childTitle = "child";
	public long childId = 3L;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.coreCategoryRoot = new Category(rootId, rootTitle, null, new Date(), CategoryConverter.defaultAccount);
		this.coreCategoryChild = new Category(childId, childTitle, coreCategoryRoot, new Date(), CategoryConverter.defaultAccount);
		this.gwtCategoryRoot = new org.tagaprice.client.gwt.shared.entities.dump.Category(rootTitle);
		this.gwtCategoryRoot.setId(rootId);
		this.gwtCategoryChild = new org.tagaprice.client.gwt.shared.entities.dump.Category(childTitle);
		this.gwtCategoryChild.setId(childId);
		this.gwtCategoryRoot.addChildCategory(gwtCategoryChild);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void convertFromCoreToGWTOnlyRootTest() {
		ICategory convertedCategory = this.converter.convertCoreCategoryToGWT(coreCategoryRoot);
		Assert.assertEquals(rootId, convertedCategory.getId());
		Assert.assertEquals(rootTitle, convertedCategory.getTitle());
	}

	@Test
	public void convertFromCoreToGWTWithChildTest() {
		ICategory convertedCategory = this.converter.convertCoreCategoryToGWT(coreCategoryChild);
		Assert.assertEquals(childId, convertedCategory.getId());
		Assert.assertEquals(childTitle, convertedCategory.getTitle());
		Assert.assertEquals(rootId, convertedCategory.getParentCategory().getId());
		Assert.assertEquals(rootTitle, convertedCategory.getParentCategory().getTitle());
	}

}

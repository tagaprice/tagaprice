package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.categorymanagement.Category;

public class CategoryTest {

	Category emptyCategory;
	Category newCategory;
	Category updateCategory;
	Category setterCategory;

	@Before
	public void setUp() throws Exception {
		emptyCategory = new Category();
		newCategory = new Category("title", new Category("parent", null));
		updateCategory = new Category("1", "2", "update", new Category("updateparent", null));
		setterCategory = new Category();
	}

	@Test
	public void testCategory() {
		assertNull(emptyCategory.getParentCategory());
		assertNull(emptyCategory.getTitle());
		assertNull(emptyCategory.getId());
		assertNull(emptyCategory.getRevision());
	}

	@Test
	public void testCategoryStringCategory() {
		assertEquals(newCategory.getTitle(), "title");
		assertNotNull(newCategory.getParentCategory());
		assertEquals(newCategory.getParentCategory().getTitle(), "parent");
		assertNull(newCategory.getId());
		assertNull(newCategory.getRevision());
	}


	@Test
	public void testGetParentCategory() {
		assertNotNull(updateCategory.getParentCategory());
		assertEquals(updateCategory.getParentCategory().getTitle(), "updateparent");
	}

	@Test
	public void testSetParentCategory() {
		setterCategory.setParentCategory(new Category("parent", null));
		assertNotNull(setterCategory.getParentCategory());
		assertEquals(setterCategory.getParentCategory().getTitle(), "parent");
		assertNull(setterCategory.getParentCategory().getParentCategory());

	}

	@Test
	public void testGetTitle() {
		assertEquals(updateCategory.getTitle(), "update");
	}

	@Test
	public void testSetTitle() {
		setterCategory.setTitle("title");
		assertEquals(setterCategory.getTitle(), "title");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updateCategory.getRevision(),"2");
	}

	@Test
	public void testSetRevision() {
		setterCategory.setRevision("15");
		assertEquals(setterCategory.getRevision(), "15");
	}

	@Test
	public void testGetId() {
		assertEquals(updateCategory.getId(),"1");
	}

	@Test
	public void testSetId() {
		setterCategory.setId("9");
		assertEquals(setterCategory.getId(), "9");
	}

}

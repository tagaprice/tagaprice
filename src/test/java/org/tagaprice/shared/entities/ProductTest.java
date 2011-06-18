package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductTest {

	Product emptyProduct;
	Product newProduct;
	Product updateProduct;
	Product setterProduct;
	
	User testUser = new User("testUID", "testRev", "Test User");

	@Before
	public void setUp() throws Exception {
		emptyProduct = new Product();
		newProduct = new Product(testUser, "newProduct", new Category(testUser, "newCategory", null), new Unit(testUser, "kg"));
		updateProduct = new Product(testUser, "1", "2", "updateProduct", new Category(testUser, "6","7","updateCategory", null), new Unit(testUser, "4","5","g"));
		setterProduct = new Product();
	}

	@Test
	public void testProduct() {
		assertNull(emptyProduct.getTitle());
		assertNull(emptyProduct.getId());
		assertNull(emptyProduct.getRevision());
		assertNull(emptyProduct.getCategory());
		assertNull(emptyProduct.getUnit());
		assertNotNull(emptyProduct.getPackages());
		assertNull(emptyProduct.getUnitId());
		assertNotNull(emptyProduct.getPackages());
		assertEquals(emptyProduct.getPackages().size(), 0);
	}

	@Test
	public void testProductStringCategoryUnit() {
		assertEquals(newProduct.getTitle(), "newProduct");
		assertNull(newProduct.getId());
		assertNull(newProduct.getRevision());
		assertNotNull(newProduct.getCategory());
		assertEquals(newProduct.getCategory().getTitle(), "newCategory");
		assertNull(newProduct.getCategory().getParent());
		assertNotNull(newProduct.getUnit());
		assertEquals(newProduct.getUnit().getTitle(), "kg");
		assertNotNull(newProduct.getPackages());
		assertEquals(newProduct.getPackages().size(), 0);
	}


	@Test
	public void testSetCategory() {
		assertNull(setterProduct.getCategory());
		setterProduct.setCategory(new Category(testUser, "setterCategory", null));
		assertNotNull(setterProduct.getCategory());
		assertEquals(setterProduct.getCategory().getTitle(), "setterCategory");
		assertNull(setterProduct.getCategory().getParent());
	}

	@Test
	public void testGetCategory() {
		assertEquals(updateProduct.getCategory().getTitle(), "updateCategory");
	}

	@Test
	public void testGetCategoryId() {
		assertEquals(updateProduct.getCategoryId(), "6");
	}

	@Test
	public void testSetCategoryId() {
		updateProduct.setCategoryId("3");
		assertEquals(updateProduct.getCategoryId(), "3");
	}

	@Test
	public void testAddPackage() {
		assertNotNull(updateProduct.getPackages());
		assertEquals(updateProduct.getPackages().size(), 0);

		updateProduct.addPackage(new Package(new Quantity(new BigDecimal("9.9"), new Unit(testUser, "l"))));
		assertEquals(updateProduct.getPackages().size(), 1);
		assertEquals(updateProduct.getPackages().get(0).getQuantity().getQuantity(), new BigDecimal("9.9"));
		assertEquals(updateProduct.getPackages().get(0).getQuantity().getUnit().getTitle(),"l");

	}

	@Test
	public void testSetPackages() {
		assertNotNull(updateProduct.getPackages());
		assertEquals(updateProduct.getPackages().size(), 0);

		updateProduct.addPackage(new Package(new Quantity(new BigDecimal("9.9"), new Unit(testUser, "l"))));
		assertEquals(updateProduct.getPackages().size(), 1);
		assertEquals(updateProduct.getPackages().get(0).getQuantity().getQuantity(), new BigDecimal("9.9"));
		assertEquals(updateProduct.getPackages().get(0).getQuantity().getUnit().getTitle(),"l");

		ArrayList<Package> plist = new ArrayList<Package>();
		plist.add(new Package(new Quantity(new BigDecimal("5.5"), new Unit(testUser, "kg"))));
		plist.add(new Package(new Quantity(new BigDecimal("4.2"), new Unit(testUser, "ml"))));
		updateProduct.setPackages(plist);
		assertEquals(updateProduct.getPackages().size(), 2);

		assertEquals(updateProduct.getPackages().get(0).getQuantity().getQuantity(), new BigDecimal("5.5"));
		assertEquals(updateProduct.getPackages().get(0).getQuantity().getUnit().getTitle(),"kg");

		assertEquals(updateProduct.getPackages().get(1).getQuantity().getQuantity(), new BigDecimal("4.2"));
		assertEquals(updateProduct.getPackages().get(1).getQuantity().getUnit().getTitle(),"ml");

	}

	@Test
	public void testGetPackages() {
		assertNotNull(updateProduct.getPackages());
		assertEquals(updateProduct.getPackages().size(), 0);
	}

	@Test
	public void testSetUnit() {
		updateProduct.setUnit(new Unit(testUser, "updateTitle"));
		assertEquals(updateProduct.getUnit().getTitle(), "updateTitle");
	}

	@Test
	public void testGetUnit() {
		assertEquals(updateProduct.getUnit().getTitle(), "g");
	}

	@Test
	public void testGetUnitId() {
		assertEquals(updateProduct.getUnitId(), "4");
	}

	@Test
	public void testSetUnitId() {
		updateProduct.setUnitId("5");
		assertEquals(updateProduct.getUnitId(), "5");
	}

	@Test
	public void testGetTitle() {
		assertEquals(updateProduct.getTitle(), "updateProduct");
	}

	@Test
	public void testSetTitle() {
		updateProduct.setTitle("updateTitle");
		assertEquals(updateProduct.getTitle(), "updateTitle");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updateProduct.getRevision(), "2");
	}

	@Test
	public void testSetRevision() {
		updateProduct.setRevision("8");
		assertEquals(updateProduct.getRevision(), "8");
	}

	@Test
	public void testGetId() {
		assertEquals(updateProduct.getId(), "1");
	}

	@Test
	public void testSetId() {
		updateProduct.setId("9");
		assertEquals(updateProduct.getId(), "9");
	}

}

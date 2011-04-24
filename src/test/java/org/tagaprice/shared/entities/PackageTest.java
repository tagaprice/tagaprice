package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;

public class PackageTest {

	Package emptyPackage;
	Package newPackage;
	Package updatePackage;
	Package setterPackage;

	@Before
	public void setUp() throws Exception {
		emptyPackage = new Package();
		newPackage = new Package(new Quantity(new BigDecimal("5.5"), new Unit("kg")));
		updatePackage = new Package("1", "2", new Quantity(new BigDecimal("12.5"), new Unit("g")));
		setterPackage = new Package();
	}

	@Test
	public void testPackage() {
		assertNull(emptyPackage.getQuantity());
		assertNull(emptyPackage.getProduct());
		assertNull(emptyPackage.getId());
		assertNull(emptyPackage.getRevision());
	}

	@Test
	public void testPackageQuantity() {
		assertEquals(newPackage.getQuantity().getQuantity(), new BigDecimal("5.5"));
		assertEquals(newPackage.getQuantity().getUnit().getTitle(), "kg");
		assertNull(newPackage.getProduct());
		assertNull(newPackage.getId());
		assertNull(newPackage.getRevision());
	}


	@Test
	public void testGetProduct() {
		assertNull(updatePackage.getProduct());
	}

	@Test
	public void testGetQuantity() {
		assertEquals(updatePackage.getQuantity().getQuantity(), new BigDecimal("12.5"));
		assertEquals(updatePackage.getQuantity().getUnit().getTitle(), "g");
	}

	@Test
	public void testSetProduct() {
		assertNull(setterPackage.getProduct());
		Product p = new Product("setterProduct", new Category("setterCategory", null), new Unit("kg"));
		setterPackage.setProduct(p);
		assertEquals(setterPackage.getProduct().getTitle(), "setterProduct");
		assertEquals(setterPackage.getProduct().getCategory().getTitle(), "setterCategory");
		assertNull(setterPackage.getProduct().getCategory().getParentCategory());
		assertEquals(setterPackage.getProduct().getUnit().getTitle(), "kg");
	}

	@Test
	public void testSetQuantity() {
		assertNull(setterPackage.getQuantity());
		setterPackage.setQuantity(new Quantity(new BigDecimal("5.6"), new Unit("g")));
		assertNotNull(setterPackage.getQuantity());
		assertEquals(setterPackage.getQuantity().getQuantity(), new BigDecimal("5.6"));
		assertEquals(setterPackage.getQuantity().getUnit().getTitle(), "g");
	}

	@Test
	public void testGetRevision() {
		assertEquals(updatePackage.getRevision(),"2");
	}

	@Test
	public void testSetRevision() {
		setterPackage.setRevision("3");
		assertEquals(setterPackage.getRevision(),"3");
	}

	@Test
	public void testGetId() {
		assertEquals(updatePackage.getId(),"1");
	}

	@Test
	public void testSetId() {
		setterPackage.setId("4");
		assertEquals(setterPackage.getId(),"4");
	}

}

package org.tagaprice.server.dao.couchdb;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductDAOTest extends AbstractDAOTest {
	CouchDBDaoFactory daoFactory = new CouchDBDaoFactory("test_");
	ProductDAO productDAO = daoFactory.getProductDAO();
	CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
	UnitDAO unitDAO = daoFactory.getUnitDAO();
	
	Category m_testCategory;
	Unit m_testUnit;

	public ProductDAOTest() {
		addDAOClass(productDAO);
		addDAOClass(categoryDAO);
		addDAOClass(unitDAO);
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		m_testCategory = new Category("catTitle", null);
		m_testUnit = new Unit("unitTitle");
		
		categoryDAO.create(m_testCategory);
		unitDAO.create(m_testUnit);
	}
	
	@Test
	public void testCreateProduct() {
		Product product = new Product("productTitle", m_testCategory, m_testUnit);
		
		productDAO.create(product);
		assertNotNull(product.getId());
		assertNotNull(product.getRevision());
		
		Product fetchedProduct = productDAO.get(product.getId());

		assertEquals(product, fetchedProduct);
	}
	
	@Test
	public void testUpdateProduct() {
		Product product = new Product("productTitle", m_testCategory, m_testUnit);
		productDAO.create(product);
		
		String oldId = product.getId();
		String oldRevision = product.getRevision();
		
		product.setTitle("newProductTitle");
		productDAO.update(product);
		
		assertEquals("The Product's ID misteriously changed", oldId, product.getId());
		assertNotSame("The Product's revision hasn't changed", oldRevision, product.getRevision());
		assertEquals("The Product's updated title hasn't been stored correctly", "newProductTitle", product.getTitle());
		
		Product fetchedProduct = productDAO.get(product.getId());
		assertEquals("The product we've just fetched from the database doesn't seem to match the original product we saved there", product, fetchedProduct);
	}
}

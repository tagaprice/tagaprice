package org.tagaprice.server.dao.couchdb;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ProductDAOTest extends AbstractDAOTest {
	IProductDAO productDAO = m_daoFactory.getProductDAO();
	ICategoryDAO categoryDAO = m_daoFactory.getCategoryDAO();
	IUnitDAO unitDAO = m_daoFactory.getUnitDAO();
	
	Category m_testCategory;
	Unit m_testUnit;


	public ProductDAOTest() throws IOException {
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		m_testCategory = new Category(m_testUser, "catTitle", null);
		m_testUnit = new Unit(m_testUser, "unitTitle");
		
		categoryDAO.create(m_testCategory);
		unitDAO.create(m_testUnit);
	}
	
	@Test
	public void testCreateProduct() throws DaoException {
		Product product = new Product(m_testUser, "productTitle", m_testCategory, m_testUnit);
		
		productDAO.create(product);
		assertNotNull(product.getId());
		assertNotNull(product.getRevision());
		
		Product fetchedProduct = productDAO.get(product.getId());

		assertEquals(product, fetchedProduct);
	}
	
	@Test
	public void testUpdateProduct() throws DaoException {
		Product product = new Product(m_testUser, "productTitle", m_testCategory, m_testUnit);
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

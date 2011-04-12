package org.tagaprice.server.dao.couchdb;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductDAOTest {
	CouchDBDaoFactory daoFactory = new CouchDBDaoFactory("test_");
	ProductDAO productDAO = (daoFactory.getProductDAO() instanceof ProductDAO) ? (ProductDAO) daoFactory.getProductDAO() : null;

	@Before
	public void setUp() throws Exception {
		if (productDAO.hasDB()) productDAO.deleteDB();
		productDAO.createDB();
	}

	@After
	public void tearDown() throws Exception {
		productDAO.deleteDB();
	}

	@Test
	public void testCreateProduct() {
		Product product = new Product("productTitle", new Category("catId", "catRev", "catTitle", null), new Unit("unitId", "revID", "grams"));
		
		productDAO.create(product);
		assertNotNull(product.getId());
		assertNotNull(product.getRevision());
		
		Product fetchedProduct = productDAO.get(product.getId());

		assertEquals(product, fetchedProduct);
	}
}

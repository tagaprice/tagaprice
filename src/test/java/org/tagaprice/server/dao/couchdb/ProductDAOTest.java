package org.tagaprice.server.dao.couchdb;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Product;

public class ProductDAOTest extends AbstractDAOTest {
	CouchDBDaoFactory daoFactory = new CouchDBDaoFactory("test_");
	ProductDAO productDAO = daoFactory.getProductDAO();

	public ProductDAOTest() {
		addDAOClass(productDAO);
		addDAOClass(daoFactory.getCategoryDAO());
		addDAOClass(daoFactory.getUnitDAO());
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

package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.*;
import org.tagaprice.core.beans.Product;
import org.tagaprice.core.beans.ProductRevision;
import org.tagaprice.server.dao.hibernate.HibernateProductDAO;
import org.tagaprice.server.dao.ints.IProductDAO;

/**
 * Testcase to test the {@link IProductDAO} interface.
 * Extend this class for each concrete ORM technology.
 * @author "haja"
 *
 */
public class AbstractProductDAOTest extends AbstractDAOTest {
	
	protected IProductDAO _productDAO;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		
		// TODO finish implementation of this test
		Product productToSave = new Product();
		ProductRevision productRevToSave = new ProductRevision("productTitle");
		productToSave.setCurrentRevision(productRevToSave);
		
		
		Product expected = new Product((long) 1);
		ProductRevision expectedProductRev = new ProductRevision("productTitle");


		Product actual = _productDAO.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

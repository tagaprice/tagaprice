package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.junit.*;
import org.springframework.transaction.annotation.Transactional;
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
	
	protected IProductDAO _productDao;

	@Before
	public void setUp() throws Exception {
		// TODO setup db
		
		_productDao = getContext().getBean("productDao", HibernateProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		
		// TODO finish implementation of this test
		Date savedDate = new Date();
		Product productToSave = new Product(0, savedDate, 0);
		productToSave.setId((long) 0);
		
		
		
		Product expected = new Product(0, savedDate, 0);
		expected.setId((long) 0);

		
		Product actual = _productDao.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.hamcrest.core.Is;
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
public class AbstractProductDaoTest extends AbstractDAOTest {
	
	protected IProductDAO _productDao;

	@Before
	public void setUp() throws Exception {
		// TODO setup db
		
		_productDao = getContext().getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		Date savedDate = new Date();
		Product productToSave = new Product(0, savedDate, 0);
		
		Product expected = new Product(1, 0, savedDate, 0);
		
		Product actual = _productDao.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

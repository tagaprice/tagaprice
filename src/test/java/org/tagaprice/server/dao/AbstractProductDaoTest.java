package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;

import org.hamcrest.core.Is;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.beans.Product;
import org.tagaprice.core.beans.ProductRevision;
import org.tagaprice.server.dao.hibernate.HibernateProductDAO;
import org.tagaprice.server.dao.ints.IProductDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Testcase to test the {@link IProductDAO} interface.
 * Extend this class for each concrete ORM technology.
 * 
 * TODO create AbstractDaoTest class
 * 
 * @author haja
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@TestExecutionListeners({})
@ContextConfiguration(locations={"/spring/test-beans.xml"})
//extension is needed for application context, otherwise it would work with the two annotations above
public class AbstractProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	protected IProductDAO _productDao;
	

	@Before
	public void setUp() throws Exception {
		// TODO setup db using DBUnit
		
		//TODO remove this and handle through xml ?
		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception { }
	
	
	@Test
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		Date savedDate = new Date();
		Product productToSave = new Product(0, savedDate, 0);
		
		Product expected = new Product(0, savedDate, 0);
		ReflectionTestUtils.invokeSetterMethod(expected, "setId", (long) 1);
		
		Product actual = _productDao.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

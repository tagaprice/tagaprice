package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import org.junit.*;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Testcase to test the {@link IProductDAO} interface.
 * Extend this class for each concrete ORM technology.
 * 
 * TODO create AbstractDaoTest class
 * 
 * @author haja
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @TestExecutionListeners({})
@ContextConfiguration(locations = { "/spring/test-beans.xml", "AbstractProductDaoTests-context.xml" })
// extension is needed for application context, otherwise it would work with the two annotations above
public class AbstractProductDaoTests extends AbstractTransactionalJUnit4SpringContextTests {

	protected IProductDAO _productDao;
	protected IDbTestInitializer _dbInitializer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TODO initialize dbInitializer here
	}

	@Before
	public void setUp() throws Exception {
		// TODO this should be in setUpBeforeClass

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		// TODO remove this and handle through xml ?
		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}


	@Test
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {

		Date localeDate = new Date();

		//		Locale locale = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(locale, "setId", 0);
		//		ReflectionTestUtils.invokeSetterMethod(locale, "setFallback", locale);

		Date savedDate = new Date();
		Product productToSave = new Product(new Long(0), "title", null, null, 0, null, null, null, null);


		//		Locale localeExpected = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(localeExpected, "setId", 0);
		//		ReflectionTestUtils.invokeSetterMethod(localeExpected, "setFallback", localeExpected);

		Product expected = new Product(new Long(0), "title", null, null, 0, null, null, null, null);
		ReflectionTestUtils.invokeSetterMethod(expected, "setId", (long) 0);


		System.out.println("expected: " + expected);
		System.out.println("toSave:   " + productToSave);

		Product actual = _productDao.save(productToSave);

		//System.out.println("actual:   " + actual);

		assertThat(actual, equalTo(expected));
	}

	@Test
	public void loadProduct_shouldReturnProductWithActualProductRevision() {
		//TODO implement

	}

}

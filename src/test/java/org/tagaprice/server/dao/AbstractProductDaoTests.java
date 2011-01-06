package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.*;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

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
	private Logger log = Logger.getLogger(AbstractProductDaoTests.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TODO initialize dbInitializer here
	}

	@Before
	public void setUp() throws Exception {
		// TODO this should be in setUpBeforeClass

		//		TODO comment in
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		// TODO remove this and handle through xml ?
		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		//		TODO comment in
		_dbInitializer.resetTables();
	}


	@Test
	@Rollback(false)//rollback can be switched here
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {


		//		Locale locale = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(locale, "setId", 0);
		//		ReflectionTestUtils.invokeSetterMethod(locale, "setFallback", locale);

		Set<ProductRevision> revisions = new  HashSet<ProductRevision>();
		revisions.add(new ProductRevision(new Long(4), "title", null, new Date(), 2, null, null, null, null));

		Product productToSave = new Product(new Long(4), null, null, null, revisions);
		System.out.println("toSave:   " + productToSave);


		//		Locale localeExpected = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		//		ReflectionTestUtils.invokeSetterMethod(localeExpected, "setId", 0);
		//		ReflectionTestUtils.invokeSetterMethod(localeExpected, "setFallback", localeExpected);

		revisions = new  HashSet<ProductRevision>();
		revisions.add(new ProductRevision(new Long(4), "title", null, new Date(), 2, null, null, null, null));

		Product expected = new Product(new Long(4), null, null, null, revisions);

		System.out.println("expected: " + expected);

		Product actual = _productDao.save(productToSave);
		System.out.println("actual:   " + actual);

		assertThat(actual, equalTo(expected));
	}

	@Test
	public void loadProduct_shouldReturnProductWithActualProductRevision() {
		//		Product key = new Product();

		//		ReflectionTestUtils.invokeSetterMethod(key, "setId", 1);
		//		ReflectionTestUtils.invokeSetterMethod(key, "setRevisionNumber",1);

		Product actual = _productDao.getById(new Long(1));

		System.out.println(actual.getId());

		for(ProductRevision pr : actual.getRevisions())
			System.out.println(pr.getId()+" "+pr.getRevisionNumber()+" "+pr.getTitle()+" "+pr.getImageURL());
	}

	@Test
	public void loadAllProducts() {
		//		Product key = new Product();

		//		ReflectionTestUtils.invokeSetterMethod(key, "setId", 1);
		//		ReflectionTestUtils.invokeSetterMethod(key, "setRevisionNumber",1);

		List<Product> products = _productDao.getAll();
		for(Product p : products) {
			System.out.println(p.getId());
		}
	}

	//	@Test
	//	public void test() {
	//		_productDao.test();
	//	}

	@Test
	public void countProducts() {
		_productDao.countAll();
	}


}

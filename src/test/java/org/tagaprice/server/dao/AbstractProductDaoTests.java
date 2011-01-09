package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.*;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.helper.DbUnitDataSetHelper;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.springframework.test.annotation.Rollback;
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
	private Logger log = Logger.getLogger(AbstractProductDaoTests.class);
	private IDataSet _currentDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO initialize dbInitializer here
	}

	@Before
	public void setUp() throws Exception {
		// TODO this should be in setUpBeforeClass

		// TODO comment in
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		// TODO remove this and handle through xml ?
		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		// TODO comment in
		_dbInitializer.resetTables();
	}


	@Test
	@Rollback(false)
	// rollback can be switched here
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {

		Date localeDate = new Date();

		Locale locale = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		ReflectionTestUtils.invokeSetterMethod(locale, "setId", 0);
		ReflectionTestUtils.invokeSetterMethod(locale, "setFallback", locale);

		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(new ProductRevision(new Long(4), "title", new Date(), 2, null, null, null, null, "someImageUrl"));

		Product productToSave = new Product(new Long(4), locale, new Date(), null, revisions);
		//		System.out.println("toSave:   " + productToSave);


		revisions = new HashSet<ProductRevision>();
		revisions.add(new ProductRevision(new Long(4), "title", new Date(), 2, null, null, null, null,  "someImageUrl"));

		Product expected = new Product(new Long(4), locale, new Date(), null, revisions);

		//		System.out.println("expected: " + expected);

		Product actual = _productDao.save(productToSave);
		//		System.out.println("actual:   " + actual);

		assertThat(actual, equalTo(expected));
	}

	@Test
	public void loadProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		ITable entityTable = _currentDataSet.getTable("entity");
		ITable entityRevTable = _currentDataSet.getTable("entityRevision");

		Locale expectedLocale = DbUnitDataSetHelper.getLocale(_currentDataSet.getTable("locale"), 0);


		ProductRevision rev1 = new ProductRevision((long) 1, "product1", DbUnitDataSetHelper.getDate(entityRevTable.getValue(0, "created_at")), 1, null, null, null, null, "www.urlToImage.com");
		ProductRevision rev2 = new ProductRevision((long) 1, "product1",  DbUnitDataSetHelper.getDate(entityRevTable.getValue(1, "created_at")), 2, null, null, null, null, "www.differentUrlToImage.com");

		// TODO fix this test after Account is mapped
		//		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		//		revisions.add(rev1);
		//		revisions.add(rev2);
		//		Product expected = new Product((long) 1, expectedLocale, DbUnitDataSetHelper.getDate(entityTable.getValue(0, "created_at")), null, revisions);

		Product actual = _productDao.getById(new Long(1));

		//		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItems(rev1, rev2));
		assertThat(actual.getRevisions().size(), equalTo(2));
	}

	@Test
	public void loadAllProducts() throws Exception {
		ITable productTable = _currentDataSet.getTable("product");
		ITable entityTable = _currentDataSet.getTable("entity");
		ITable localeTable = _currentDataSet.getTable("locale");

		List<Product> products = _productDao.getAll();

		for(int i = 0; i < products.size(); i++) {
			long id = DbUnitDataSetHelper.getLong(productTable.getValue(i, "ent_id"));
			Date createdAt = DbUnitDataSetHelper.getDate(entityTable.getValue(i, "created_at"));

			// assumed all products have same Locale
			Locale expectedLocale = DbUnitDataSetHelper.getLocale(localeTable, 0);

			int locale_id = DbUnitDataSetHelper.getInteger(entityTable.getValue(i, "locale_id"));
			assertThat("testsetup is bad, we assume only the first locale is used for all products", locale_id, equalTo(expectedLocale.getId()));


			//Product expected = new Product(id, expectedLocale, createdAt, null, revisions);
			Product actual = products.get(i);
			assertThat(actual.getId(), equalTo(id));
			assertThat(actual.getCreatedAt(), equalTo(createdAt));
			assertThat(actual.getLocale(), equalTo(expectedLocale));
		}
	}

	// @Test
	// public void test() {
	// _productDao.test();
	// }

	@Test
	public void countProducts() throws Exception {
		ITable table = _currentDataSet.getTable("product");

		int actual = _productDao.countAll();

		int expected = table.getRowCount();

		assertThat(actual, equalTo(expected));
	}


}

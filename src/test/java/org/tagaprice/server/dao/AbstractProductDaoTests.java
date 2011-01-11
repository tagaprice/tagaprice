package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.*;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.helper.DbUnitDataSetHelper;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger _log = LoggerFactory.getLogger(AbstractProductDaoTests.class);
	private IDataSet _currentDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO initialize dbInitializer here
	}

	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");
		// TODO this should be in setUpBeforeClass

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		//		_dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		long id = 4;
		Product productToSave = getProductToSaveWithOneRevision(id);

		ProductRevision newRev = new ProductRevision(id, "title", new Date(), 1, null, null, null, null,  "someImageUrl");

		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(newRev);

		Product expected = new Product(id, getLocaleWithConstantDate(), new Date(), null, revisions);

		Product actual = _productDao.save(productToSave);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItem(newRev));
	}

	@Test
	@Rollback(false)
	public void saveUpdatedProduct_shouldReturnProductWithUpdatedProductRevision() {
		long id = 4;
		Product productToSave = getProductToSaveWithOneRevision(id);

		Product actual = _productDao.save(productToSave);

		ProductRevision newRev = new ProductRevision(id, "newRevTitle", new Date(), 2, null, null, null, null, "newRevImage.url");
		actual.getRevisions().add(newRev);

		Product updated = _productDao.save(actual);

		assertThat(updated.getRevisions(), hasItem(newRev));
		assertThat(updated.getId(), equalTo(id));
	}


	@Test
	public void loadProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		ITable entityRevTable = _currentDataSet.getTable("entityRevision");

		// TODO fix this test after Account is mapped
		ProductRevision rev1 = new ProductRevision((long) 1, "product1", DbUnitDataSetHelper.getDate(entityRevTable.getValue(0, "created_at")), 1, null, null, null, null, "www.urlToImage.com");
		ProductRevision rev2 = new ProductRevision((long) 1, "product1",  DbUnitDataSetHelper.getDate(entityRevTable.getValue(1, "created_at")), 2, null, null, null, null, "www.differentUrlToImage.com");

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
			Integer currentRevision = DbUnitDataSetHelper.getInteger(entityTable.getValue(i, "current_revision"));

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

	@Test
	public void countProducts() throws Exception {
		ITable table = _currentDataSet.getTable("product");

		int actual = _productDao.countAll();

		int expected = table.getRowCount();

		assertThat(actual, equalTo(expected));
	}



	// **************************************************************
	//
	// helpers
	//
	// **************************************************************


	private Product getProductToSaveWithOneRevision(long id) {
		Locale locale = getLocaleWithConstantDate();

		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		revisions.add(new ProductRevision(id, "title", new Date(), 1, null, null, null, null, "someImageUrl"));

		Product productToSave = new Product(id, locale, new Date(), null, revisions);
		return productToSave;
	}

	private Locale getLocaleWithConstantDate() {
		Date localeDate = new Date(1000);

		Locale locale = new Locale(null, "testTitle", "testLocalTitle", localeDate);
		ReflectionTestUtils.invokeSetterMethod(locale, "setId", 0);
		ReflectionTestUtils.invokeSetterMethod(locale, "setFallback", locale);
		return locale;
	}
}

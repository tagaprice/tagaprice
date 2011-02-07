package org.tagaprice.server.dao.interfaces;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Unit;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.DbUnitDataSetHelper;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;
import org.tagaprice.server.dao.helper.ProductComparator;

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
@ContextConfiguration
// (locations = { "/spring/test-beans.xml", "AbstractProductDaoTest-context.xml" })
// extension is needed for application context, otherwise it would work with the two annotations above
public class AbstractProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected IProductDAO _productDao;
	protected IDbTestInitializer _dbInitializer;
	private Logger _log = LoggerFactory.getLogger(AbstractProductDaoTest.class);
	private IDataSet _currentDataSet;
	private SessionFactory _sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		// TODO this should be in setUpBeforeClass
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_productDao = applicationContext.getBean("productDao", IProductDAO.class);

		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}

	@After
	public void tearDown() throws Exception {
		// _dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void saveNewProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		_log.info("running test");

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category category = HibernateSaveEntityCreator.createCategory(null, creator);

		Long id = null;
		int numberRevisions = 2;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevisions(id, numberRevisions, creator, Unit.ml, category));

		Product actual = _productDao.save(productToSave);

		Long expectedId = 4L; //TODO use nextFreeProductId from hibernatesaveentitycreator when implemented
		Set<ProductRevision> expectedRevisions = new HashSet<ProductRevision>();
		ProductRevision expectedRev = HibernateSaveEntityCreator.createProductRevision(expectedId, 1, creator, Unit.ml,
				category);
		expectedRevisions.add(expectedRev);
		Product expected = new Product(expectedId, HibernateSaveEntityCreator.createLocale(2), expectedRevisions);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItem(expectedRev));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : expectedRevisions) //TODO why does this that _expectedRevisions_ are saved => not actual revisions (wont make a difference probably...)
			DbSaveAssertUtility.assertEntitySaved(rev);
	}

	@Test
	@Rollback(false)
	public void saveExistingProduct_shouldPersistProduct_shouldReturnProductAsGiven() throws Exception {
		//TODO fix this test 
		//this test fails due to hibernate executing sql code: "set ent_id=null where ent_id=?" - no idea why, corresponding integration test runs
		
		_log.info("running test");

		
		Long id = 1L;
		Integer revId = 3;
		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		Category category1 = HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", creator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 1, "coke", creator, Unit.g, 100.0, category1, "www.urlToImage.com"));
		Category category2 = HibernateSaveEntityCreator.createCategory(2L, category1, "category1", creator);
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, 2, "original coke", creator, Unit.g, 100.0, category2, "www.differentUrlToImage.com"));
		revisions.add(HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, category2));
		
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				revisions);

		Product actual = _productDao.save(productToSave);

		Long expectedId = 1L;
		Account expectedCreator = HibernateSaveEntityCreator.createAccount(1L);
		Set<ProductRevision> expectedRevisions = new HashSet<ProductRevision>();
		Category expectedCategory1 = HibernateSaveEntityCreator.createCategory(1L, null, "rootCategory", expectedCreator);
		expectedRevisions.add(HibernateSaveEntityCreator.createProductRevision(id, 1, "coke", expectedCreator, Unit.g, 100.0, expectedCategory1, "www.urlToImage.com"));
		Category expectedCategory2 = HibernateSaveEntityCreator.createCategory(1L, expectedCategory1, "category1", expectedCreator);
		expectedRevisions.add(HibernateSaveEntityCreator.createProductRevision(id, 2, "original coke", expectedCreator, Unit.g, 100.0, expectedCategory2, "www.differentUrlToImage.com"));
		expectedRevisions.add(HibernateSaveEntityCreator.createProductRevision(id, revId,	creator, Unit.ml, HibernateSaveEntityCreator.createCategory(null, creator)));
		Product expected = HibernateSaveEntityCreator.createProduct(expectedId,
				HibernateSaveEntityCreator.createLocale(1),
				expectedRevisions);


		ProductComparator.compareProductsAndRevisions(actual, expected);

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : actual.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}


	@Test
	@Rollback(false)
	public void loadProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		_log.info("running test");

		Long productId = 1L;
		Long creatorId = 1L;
		Product actual = _productDao.getById(productId);

		Account creator = HibernateSaveEntityCreator.createAccount(creatorId);
		Category cat1 = HibernateSaveEntityCreator.createCategory(
				1L,
				null,
				"rootCategory",
				creator);
		Category cat2 = HibernateSaveEntityCreator.createCategory(
				2L,
				cat1,
				"category1",
				creator);

		ProductRevision rev1 = HibernateSaveEntityCreator.createProductRevision(
				productId,
				1,
				"coke",
				creator,
				cat1,
		"www.urlToImage.com");

		ProductRevision rev2 = HibernateSaveEntityCreator.createProductRevision(
				productId,
				2,
				"original coke",
				creator,
				cat2,
		"www.differentUrlToImage.com");

		assertThat(actual.getRevisions(), hasItems(rev1, rev2));
		assertThat(actual.getRevisions().size(), equalTo(2));
	}

	@Test
	public void getById_unknownId_shouldReturnNull() throws Exception {
		_log.info("running test");

		Long unkownId = 100L; // this id should not be in the dataset
		Product actual = _productDao.getById(unkownId);

		assertThat(actual, equalTo(null));
	}


	@Test
	public void loadAllProducts() throws Exception {
		_log.info("running test");

		ITable productTable = _currentDataSet.getTable("product");

		List<Product> products = _productDao.getAll();

		for (int i = 0; i < products.size(); i++) {
			long id = DbUnitDataSetHelper.getLong(productTable.getValue(i, "ent_id"));

			// Product expected = new Product(id, expectedLocale, createdAt, null, revisions);
			Product actual = products.get(i);
			assertThat(actual.getId(), equalTo(id));
		}
	}

	@Test
	public void countProducts() throws Exception {
		_log.info("running test");

		ITable table = _currentDataSet.getTable("product");

		int actual = _productDao.countAll();

		int expected = table.getRowCount();

		assertThat(actual, equalTo(expected));
	}
}

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
	public void saveProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		_log.info("running test");

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category category = HibernateSaveEntityCreator.createCategory(null, creator);

		long id = 4;
		int numberRevisions = 2;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevisions(id, numberRevisions, creator, Unit.ml, category));

		Product actual = _productDao.save(productToSave);

		Set<ProductRevision> expectedRevisions = new HashSet<ProductRevision>();
		ProductRevision expectedRev = HibernateSaveEntityCreator.createProductRevision(id, 1, creator, Unit.ml,
				category);
		expectedRevisions.add(expectedRev);
		Product expected = new Product(id, HibernateSaveEntityCreator.createLocale(2), expectedRevisions);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItem(expectedRev));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
		for (ProductRevision rev : expectedRevisions)
			DbSaveAssertUtility.assertEntitySaved(rev);
	}

	@Test
	@Rollback(false)
	public void saveUpdatedProduct_shouldReturnProductWithUpdatedProductRevision() throws Exception {
		_log.info("running test");

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category category = HibernateSaveEntityCreator.createCategory(null, creator);

		long id = 4;
		int numberRevisions = 2;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id,
				HibernateSaveEntityCreator.createLocale(1),
				HibernateSaveEntityCreator.createProductRevisions(id, numberRevisions, creator, Unit.ml, category));

		Product saved = _productDao.save(productToSave);

		int revisionNumber = 3;
		ProductRevision newRev = HibernateSaveEntityCreator.createProductRevision(id, revisionNumber, creator, Unit.ml,
				category);

		saved.getRevisions().add(newRev);

		Product updated = _productDao.save(saved);

		assertThat(updated.getRevisions(), hasItem(newRev));
		assertThat(updated.getId(), equalTo(id));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(saved);
		DbSaveAssertUtility.assertEntitySaved(updated);
		for (ProductRevision rev : updated.getRevisions())
			DbSaveAssertUtility.assertEntitySaved(rev);
	}


	@Test
	@Rollback(false)
	public void saveUpdatedProduct_productHasOnlyNewestRevision_shouldNotDeleteOldRevs() throws Exception {
		_log.info("running test");

		Account creator = HibernateSaveEntityCreator.createAccount(1L);
		Category category = HibernateSaveEntityCreator.createCategory(null, creator);
		Locale locale = HibernateSaveEntityCreator.createLocale(1);

		long id = 4;
		int numberRevisions = 2;
		Product productToSave = HibernateSaveEntityCreator.createProduct(id, locale,
				HibernateSaveEntityCreator.createProductRevisions(id, numberRevisions, creator, Unit.ml, category));

		Product saved = _productDao.save(productToSave);

		// copy original revs
		Set<ProductRevision> originalRevs = new HashSet<ProductRevision>();
		originalRevs.addAll(saved.getRevisions());
		assertThat(originalRevs.size(), is(2));

		int revisionNumber = 3;
		ProductRevision newRev = HibernateSaveEntityCreator.createProductRevision(id, revisionNumber, creator, Unit.ml,
				category);

		Product productWithJustNewRev = saved;
		saved.getRevisions().clear();
		saved.getRevisions().add(newRev);
		assertThat(saved.getRevisions().size(), is(1));


		Product updated = _productDao.save(productWithJustNewRev);

		assertThat(updated.getRevisions(), hasItem(newRev));
		assertThat(updated.getId(), equalTo(id));

		_sessionFactory.getCurrentSession().flush();
		// assert updates
		DbSaveAssertUtility.assertEntitySaved(updated);
		DbSaveAssertUtility.assertEntitySaved(newRev);

		// assert original revisions
		for (ProductRevision rev : originalRevs)
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

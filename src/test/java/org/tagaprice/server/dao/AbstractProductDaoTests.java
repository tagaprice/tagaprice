package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
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
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.helper.DbUnitDataSetHelper;
import org.tagaprice.server.dao.helper.EntityCreator;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.interfaces.IProductDAO;

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
		// _dbInitializer.resetTables();
	}

	@Test
	@Rollback(false)
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		long id = 4;
		Product productToSave = EntityCreator.createProductWithRevisions(id, 1, 2);

		Product actual = _productDao.save(productToSave);

		Set<ProductRevision> expectedRevisions = new HashSet<ProductRevision>();
		ProductRevision expectedRev = EntityCreator.createProductRevision(id, 1);
		expectedRevisions.add(expectedRev);
		Product expected = new Product(id, EntityCreator.createLocale(2), expectedRevisions);

		assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItem(expectedRev));
	}

	@Test
	@Rollback(false)
	public void saveUpdatedProduct_shouldReturnProductWithUpdatedProductRevision() {
		long id = 4;
		long uid = 2;
		Product productToSave = EntityCreator.createProductWithRevisions(id, 1, 2);

		Product saved = _productDao.save(productToSave);

		ProductRevision newRev = new ProductRevision(id, 2, "newRevTitle", new Date(), new Account(uid,
				"test@email.com", new Date(1000000)), null, null, null, "newRevImage.url");
		saved.getRevisions().add(newRev);

		Product updated = _productDao.save(saved);

		assertThat(updated.getRevisions(), hasItem(newRev));
		assertThat(updated.getId(), equalTo(id));
	}


	@Test
	@Rollback(false)
	public void loadProduct_shouldReturnProductWithActualProductRevision() throws Exception {
		_log.info("running test");

		ITable entityRevTable = _currentDataSet.getTable("entityRevision");
		ITable accountTable = _currentDataSet.getTable("account");
		Account creator = new Account(3L, "user@mail.com", DbUnitDataSetHelper.getDate(accountTable.getValue(0,
		"last_login")));

		ProductRevision rev1 = new ProductRevision(1L, 1, (String) entityRevTable.getValue(0, "title"),
				DbUnitDataSetHelper.getDate(entityRevTable.getValue(0, "created_at")), creator, null, null, null, "www.urlToImage.com");
		ProductRevision rev2 = new ProductRevision(1L, 2, (String) entityRevTable.getValue(1, "title"),
				DbUnitDataSetHelper.getDate(entityRevTable.getValue(1, "created_at")), creator, null, null, null, "www.differentUrlToImage.com");

		// Set<ProductRevision> revisions = new HashSet<ProductRevision>();
		// revisions.add(rev1);
		// revisions.add(rev2);
		// Product expected = new Product((long) 1, expectedLocale, DbUnitDataSetHelper.getDate(entityTable.getValue(0,
		// "created_at")), null, revisions);

		Product actual = _productDao.getById(1L);

		// assertThat(actual, equalTo(expected));
		assertThat(actual.getRevisions(), hasItems(rev1, rev2));
		assertThat(actual.getRevisions().size(), equalTo(2));
	}

	@Test
	public void loadAllProducts() throws Exception {
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
		ITable table = _currentDataSet.getTable("product");

		int actual = _productDao.countAll();

		int expected = table.getRowCount();

		assertThat(actual, equalTo(expected));
	}
}

package org.tagaprice.server.dao.interfaces;


import java.util.List;
import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.helper.IDbTestInitializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author haja
 */
@ContextConfiguration
public class AbstractProductRevisionDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected IProductRevisionDAO _productRevisionDao;
	protected IDbTestInitializer _dbInitializer;
	private Logger _log = LoggerFactory.getLogger(AbstractProductRevisionDaoTest.class);
	@SuppressWarnings("unused")
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

		_productRevisionDao = applicationContext.getBean("productRevisionDao", IProductRevisionDAO.class);
	}

	@After
	public void tearDown() throws Exception {
		_dbInitializer.resetTables();
	}

	@Test
	public void getByTitle_shouldReturnAllProductsRevisionsWithCokeInTitle() {
		String title = "coke";
		List<ProductRevision> actual = _productRevisionDao.getByTitle(title);
		_log.debug("number of productrevision "+actual.size());
		int count=0;
		for(ProductRevision p : actual) {
			p.getTitle().contains(title);
			_log.debug(p.getTitle());
			count++;
		}
		assertThat(count, is(3));
	}
}

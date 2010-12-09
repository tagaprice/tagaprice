package org.tagaprice.server.dao;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;
import org.tagaprice.core.beans.Product;
import org.tagaprice.server.dao.helper.IDbTestInitializer;
import org.tagaprice.server.dao.ints.IProductDAO;
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
@ContextConfiguration(locations = { "/spring/test-beans.xml", "AbstractProductDaoTest-context.xml" })
// extension is needed for application context, otherwise it would work with the two annotations above
public class AbstractProductDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected IProductDAO _productDao;
	protected IDbTestInitializer _dbInitializer;
	protected List<String> _affectedTables = new LinkedList<String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//TODO initialize dbInitializer here
	}

	@Before
	public void setUp() throws Exception {
		// TODO this should be in setUpBeforeClass
		_affectedTables.add("entity");
		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		// TODO configure this through spring config
		//		_dbInitializer = new DbTestInitializer((DataSource) applicationContext.getBean("dataSource"), _affectedTables,
		//				new LinkedList<Resource>());


		// TODO setup db using DBUnit
		_dbInitializer.dropAndRecreate();
		_dbInitializer.fillTables();

		// TODO remove this and handle through xml ?
		_productDao = applicationContext.getBean("productDao", IProductDAO.class);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	/** TODO adapt test to use EntityRevision */
	public void saveProduct_shouldReturnProductWithActualProductRevision() {
		Date savedDate = new Date();
		Product productToSave = new Product(0, savedDate, 0);

		Product expected = new Product(0, savedDate, 0);
		ReflectionTestUtils.invokeSetterMethod(expected, "setId", (long) 0);

		Product actual = _productDao.save(productToSave);

		assertThat(actual, equalTo(expected));
	}

}

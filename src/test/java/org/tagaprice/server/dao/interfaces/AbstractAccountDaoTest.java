package org.tagaprice.server.dao.interfaces;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.dbunit.dataset.IDataSet;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.tagaprice.core.api.WrongEmailOrPasswordException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.server.boot.dbinit.IDbTestInitializer;
import org.tagaprice.server.dao.helper.DbSaveAssertUtility;
import org.tagaprice.server.dao.helper.HibernateSaveEntityCreator;

@ContextConfiguration
public class AbstractAccountDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	protected IAccountDAO _accountDao;
	protected IDbTestInitializer _dbInitializer;
	private Logger _log = LoggerFactory.getLogger(AbstractProductDaoTest.class);
	@SuppressWarnings("unused")
	private IDataSet _currentDataSet;
	private SessionFactory _sessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_log.info("Setting up tests.");

		_dbInitializer = applicationContext.getBean("dbTestInitializer", IDbTestInitializer.class);

		_dbInitializer.dropAndRecreate();
		_currentDataSet = _dbInitializer.fillTables();

		_accountDao = applicationContext.getBean("accountDao", IAccountDAO.class);

		_sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		DbSaveAssertUtility.setSimpleJdbcTemplate(super.simpleJdbcTemplate);
	}

	@After
	public void tearDown() throws Exception {
		//_dbInitializer.resetTables(); //this causes infinite loop on save test
	}

	@Test(expected=WrongEmailOrPasswordException.class)
	public void getByEmailAndPassword_noUserForEmail_shouldThrowWrongEmailOrPasswordException() throws WrongEmailOrPasswordException {
		String email = "nosuch@mail.com";
		String password = "12345";

		_accountDao.getByEmailAndPassword(email, password);
	}

	@Test
	public void getByEmailAndPassword_shouldReturnAccount() throws WrongEmailOrPasswordException {
		String email = "user1@mail.com";
		String password = "12345";

		Account actual = _accountDao.getByEmailAndPassword(email, password);

		Account expected = HibernateSaveEntityCreator.createAccount(1L, email, password);

		assertThat(actual, is(expected));
	}

	@Test
	public void saveNewAccount_shouldPersist() throws Exception {
		String email = "userX@mymail.com";
		String password = "123";

		Account accountToSave = HibernateSaveEntityCreator.createAccount(null, email, password); // new accounts don't have any id

		Account actual = _accountDao.save(accountToSave);

		Long nextAccountId = 3L; // TODO these settings should be in EntityCreator
		Account expected = HibernateSaveEntityCreator.createAccount(nextAccountId, email, password);

		assertThat(actual, is(expected));

		_sessionFactory.getCurrentSession().flush();
		DbSaveAssertUtility.assertEntitySaved(actual);
	}


}

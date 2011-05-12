package org.tagaprice.server.dao.couchdb;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jcouchdb.db.Server;
import org.junit.After;
import org.junit.Before;
import org.tagaprice.shared.entities.accountmanagement.User;

/**
 * Base class for CouchDB DAO unit tests that provides automatic DB cleanup
 */
public class AbstractDAOTest {
	private String m_dbName;
	private Server m_server;
	
	protected CouchDbDaoFactory m_daoFactory = new CouchDbDaoFactory();
	protected User m_testUser;
	
	public static class TestInjector extends InitialInjector {
		protected File _getDirFile(String path) {
			String pathPrefix = System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/classes/";
			return new File(pathPrefix+path);
		}
	}
	
	public AbstractDAOTest() throws IOException {
		CouchDbConfig dbConfig = _getConfiguration();
		
		m_dbName = dbConfig.getCouchDatabase();
		CouchDbDaoFactory._setConfiguration(dbConfig);
		m_server = CouchDbDaoFactory.getServerObject();
		
		_injectDB(); // make sure the TestInjector gets to do this instead of the original one
	}
	
	private static CouchDbConfig _getConfiguration() throws IOException {
		String pathPrefix = System.getProperty("user.dir")+"/src/test/resources/";
		CouchDbConfig rc = new CouchDbConfig();
		InputStream stream = new FileInputStream(new File(pathPrefix+"/couchdb.properties")); 
		if (stream.available() > 0) rc.load(stream);
		else throw new IOException("Couldn't load resource file 'couchdb.properties'. Make sure it exists and is accessible");
		return rc;
	}

	
	@Before
	public void setUp() throws Exception {
		// recreate the database for every test
		_dropDB();
		_injectDB();
		
		m_testUser = m_daoFactory.getUserDao().create(new User("testUser"));
	}

	@After
	public void tearDown() throws Exception {
		// drop the db after every test
		_dropDB();
	}
	
	private void _dropDB() {
		if (m_server.listDatabases().contains(m_dbName)) {
			m_server.deleteDatabase(m_dbName);
		}
	}
	
	private void _injectDB() throws IOException {
		InitialInjector injector = new TestInjector();
		_dropDB();
		injector.init(m_server, m_dbName);
	}

}

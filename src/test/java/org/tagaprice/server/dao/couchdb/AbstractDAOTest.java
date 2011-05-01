package org.tagaprice.server.dao.couchdb;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jcouchdb.db.Server;
import org.junit.After;
import org.junit.Before;

/**
 * Base class for CouchDB DAO unit tests that provides automatic DB cleanup
 */
public class AbstractDAOTest {
	private String m_dbName;
	private Server m_server;
	
	public static class TestInjector extends InitialInjector {
		protected File _getDirFile(String path) {
			String pathPrefix = System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/classes/";
			return new File(pathPrefix+path);
		}
	}
	
	public AbstractDAOTest() throws IOException {
		Properties dbConfig = _getConfiguration();
		
		m_dbName = dbConfig.getProperty("database", "tagaprice");
		CouchDBDaoFactory._setConfiguration(dbConfig);
		m_server = CouchDBDaoFactory.getServerObject();
		
		_injectDB(); // make sure the TestInjector gets to do this instead of the original one
	}
	
	private static Properties _getConfiguration() throws IOException {
		String pathPrefix = System.getProperty("user.dir")+"/src/test/resources/";
		Properties defaults = new Properties();
		try {
			InputStream stream = new FileInputStream(new File(pathPrefix+"/couchdb.properties.default"));
			if (stream.available() > 0) defaults.load(stream);
		}
		catch (IOException e) { /* ignore if we can't read the default config as long as we can read the specific one */ }
		
		Properties rc = new Properties(defaults);
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

package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

/**
 * Base class for CouchDB DAO unit tests that provides automatic DB cleanup
 */
public class AbstractDAOTest {
	List<DAOClass<?>> m_daoClasses = new ArrayList<DAOClass<?>>();
	
	/**
	 * Add a DAO class to the automatic cleanup list
	 * @param daoClass DAO class to add to the list
	 */
	protected void addDAOClass(DAOClass<?> daoClass) {
		m_daoClasses.add(daoClass); 
	}
	
	@Before
	public void setUp() throws Exception {
		for (DAOClass<?> daoClass: m_daoClasses) {
			if (daoClass.hasDB()) daoClass.deleteDB();
			daoClass.createDB();
		}
	}

	@After
	public void tearDown() throws Exception {
		for (DAOClass<?> daoClass: m_daoClasses) {
			daoClass.deleteDB();
		}
	}

}

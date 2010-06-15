/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: EntityDAOTest.java
 * Date: 15.06.2010
*/
package org.tagaprice.server.dao;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class EntityDAOTest {
	public class TestDBConnection extends DBConnection {
		public TestDBConnection() throws FileNotFoundException, IOException, SQLException {
			super();
			super.setAutoCommit(false);
		}
		
		@Override
		public void commit() {
			// DO NOTHING
		}
		
		@Override
		public InputStream _loadResourceFile(String fileName) throws FileNotFoundException {
			File file = new File("war/WEB-INF/conf/jdbc.properties");
			return new FileInputStream(file);
		}
	}
	
	private class TestEntity extends Entity {
		private static final long serialVersionUID = 1L;

		public TestEntity(Long id, int rev, String title, int localeId) {
			super(id, rev, title, localeId);
		}
		
		public TestEntity(Long id, int rev) {
			super(id, rev);
		}

		@Override
		public String getSerializeName() {
			return "testEntity";
		}
		
	}
	
	private TestDBConnection db;
	private EntityDAO dao;

	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		dao = new EntityDAO(db) {};
		
	}

	@After
	public void tearDown() throws Exception {
		db.rollback();
	}

	@Test
	public void testCreate() throws Exception {
		TestEntity entity = new TestEntity(null, 0, "entityTitle", -8), newEntity;
		dao.save(entity);
		
		assertNotSame("EntityDAO.save() should set the ID of the entity to the ID of the newly created database entry", null, entity.getId());
		newEntity = new TestEntity(entity.getId(), entity.getRev());
		
		dao.get(newEntity);
		
		assertEquals(entity, newEntity);
	}
	
	/*
	 * if you set id to NULL, revision has to be 0 too
	 */
	@Test
	public void testCreate_invalidRevision() throws Exception {
		TestEntity entity = new TestEntity(null, 13);
		try {
			dao.save(entity);
			fail("RevisionCheckException should've been thrown already.");
		} catch (RevisionCheckException e) {
			// everything's fine
		}
	}
	
	@Test
	public void testCreate_invalidLocale() throws Exception {
		TestEntity entity = new TestEntity(null, 0, "title", -1);
		try {
			dao.save(entity);
			fail("InvalidLocaleException should've been thrown already");
		}
		catch (InvalidLocaleException e) {
			// everything's fine
		}
	}
}

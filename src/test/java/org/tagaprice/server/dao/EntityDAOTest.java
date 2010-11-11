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
import org.tagaprice.shared.data.AccountData;
import org.tagaprice.shared.data.Entity;
import org.tagaprice.shared.exception.InvalidLocaleException;
import org.tagaprice.shared.exception.RevisionCheckException;

public class EntityDAOTest {
	public static class TestDBConnection extends DBConnection {
		public TestDBConnection() throws FileNotFoundException, IOException, SQLException {
			super();
			super.begin();
		}
		
		@Override
		public void commit() throws SQLException {
			if (transactionDepth > 0) transactionDepth--;
		}
		
		@Override
		public void forceRollback() throws SQLException {
			super.forceRollback();
			super.begin();
		}
		
		@Override
		public InputStream _loadResourceFile(String fileName) throws FileNotFoundException {
			File file = new File("target/test-classes/WEB-INF/conf/jdbc.properties");
			return new FileInputStream(file);
		}
	}
	
	public static class TestEntity extends Entity {
		private static final long serialVersionUID = 1L;

		public TestEntity() {
			super();
		}
		
		public TestEntity(Long id) {
			super(id);
		}
		
		public TestEntity(Long id, int rev) {
			super(id, rev);
		}
		
		public TestEntity(Long id, int rev, String title, Long revCreatorId) {
			super(id, rev, title, revCreatorId);
		}		

		public TestEntity(String title, int localeId, Long creatorId) {
			super(title, localeId, creatorId);
		}
		
		public TestEntity(Long id, int rev, String title, Integer localeId, Long creatorId, Long revCreatorId) {
			super(id, rev, title, localeId, creatorId, revCreatorId);
		}

		@Override
		public String getSerializeName() {
			return "testEntity";
		}
		
	}
	
	protected TestDBConnection db;
	private EntityDAO dao;
	private LocaleDAO localeDAO;
	private AccountData testAccount;

	@Before
	public void setUp() throws Exception {
		db = new TestDBConnection();
		dao = new EntityDAO(db);
		localeDAO = new LocaleDAO(db);
		testAccount = new AccountData("testAccount", localeDAO.get("English").getId(), "mail@example.net", null);
		new AccountDAO(db).save(testAccount);
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
		TestEntity entity = new TestEntity("entityTitle", localeDAO.get("English").getId(), testAccount.getId()), newEntity;
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
		TestEntity entity = new TestEntity(null, 13, "title", localeDAO.get("English").getId(), testAccount.getId(), testAccount.getId());
		try {
			dao.save(entity);
			fail("RevisionCheckException should've been thrown.");
		} catch (RevisionCheckException e) {
			// everything's fine
		}
	}
	
	/**
	 * try to create an entity with an invalid locale ID
	 */
	@Test
	public void testCreate_invalidLocale() throws Exception {
		TestEntity entity = new TestEntity("title", -1, testAccount.getId());
		try {
			dao.save(entity);
			fail("InvalidLocaleException should've been thrown");
		}
		catch (InvalidLocaleException e) {
			// everything's fine
		}
	}
	
	/**
	 * trying to successfully save an Entity
	 * @throws Exception
	 */
	@Test
	public void testSave() throws Exception {
		TestEntity entity = new TestEntity("title", localeDAO.get("English").getId(), testAccount.getId());
		dao.save(entity);
		entity.setTitle("newTitle");
		dao.save(entity);
		
		assertEquals(2, entity.getRev());

		// new revisions
		TestEntity e1 = new TestEntity(entity.getId(), 1);
		TestEntity e2 = new TestEntity(entity.getId(), 2);
		dao.get(e1);
		dao.get(e2);

		assertEquals(entity.getId(), e1.getId());
		assertEquals(1, e1.getRev());
		assertEquals("title", e1.getTitle());
		assertEquals(entity.getLocaleId(), e1.getLocaleId());

		assertEquals(entity.getId(), e2.getId());
		assertEquals(entity.getRev(), e2.getRev());
		assertEquals(2, e2.getRev());
		assertEquals("newTitle", e2.getTitle());
		assertEquals(entity.getLocaleId(), e2.getLocaleId());
	}
}

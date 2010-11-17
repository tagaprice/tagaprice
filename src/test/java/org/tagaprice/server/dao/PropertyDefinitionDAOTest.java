package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.AccountDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.server.dao.postgres.PropertyDefinitionDAO;
import org.tagaprice.shared.entities.Account;
import org.tagaprice.shared.entities.PropertyTypeDefinition;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;

public class PropertyDefinitionDAOTest {
	private PropertyDefinitionDAO dao;
	private DBConnection db;
	private int localeId;
	private long uid;

	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new PropertyDefinitionDAO(db);
		localeId = new LocaleDAO(db).get("English").getId();
		Account a = new Account("testAccount", localeId, "foo@test.invalid", null);
		new AccountDAO(db).save(a);
		uid = a.getId();
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
		PropertyTypeDefinition pdef = new PropertyTypeDefinition("testWeight", "Test property named 'weight'", localeId, uid, Datatype.STRING, 5, 27, null, true);
		dao.save(pdef);
		PropertyTypeDefinition pdef2 = new PropertyTypeDefinition(pdef.getId());
		dao.get(pdef2);
		assertEquals(pdef, pdef2);
	}

}

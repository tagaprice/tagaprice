package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.AccountData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.PropertyDefinition.Datatype;

public class PropertyDefinitionDAOTest {
	private PropertyDefinitionDAO dao;
	private DBConnection db;
	private int localeId;
	private long uid;

	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = PropertyDefinitionDAO.getInstance(db);
		localeId = LocaleDAO.getInstance(db).get("English").getId();
		AccountData a = new AccountData("testAccount", localeId, "foo@test.invalid", null);
		AccountDAO.getInstance(db).save(a);
		uid = a.getId();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() throws Exception {
		PropertyDefinition pdef = new PropertyDefinition("testWeight", "Test property named 'weight'", localeId, uid, Datatype.STRING, 5, 27, null, true);
		dao.save(pdef);
		PropertyDefinition pdef2 = new PropertyDefinition(pdef.getId());
		dao.get(pdef2);
		assertEquals(pdef, pdef2);
	}

}

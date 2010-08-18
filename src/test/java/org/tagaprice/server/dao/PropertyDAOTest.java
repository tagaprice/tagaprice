package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.EntityDAOTest.TestEntity;
import org.tagaprice.shared.Account;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyDefinition;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.PropertyDefinition.Datatype;

public class PropertyDAOTest {
	private TestEntity testEntity;
	private PropertyDefinition testPropDef;
	private int localeId;
	private long uid;
	private DBConnection db;
	private PropertyDAO dao;
	private PropertyDefinitionDAO propDefDAO;
	
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = PropertyDAO.getInstance(db);
		propDefDAO = PropertyDefinitionDAO.getInstance(db);
		
		localeId = LocaleDAO.getInstance().get("English").getId();
		Account a = new Account("propertyTestAccount", localeId);
		AccountDAO.getInstance(db).save(a);
		uid = a.getId();
		
		testPropDef = new PropertyDefinition("testProperty", "Test Property", localeId, uid, Datatype.DOUBLE, null, null, null, true);
		propDefDAO.save(testPropDef);
		
		testEntity = new TestEntity("Title", localeId, uid);

		EntityDAO.getInstance(db).save(testEntity);
		
		SearchResult<PropertyData> props = new SearchResult<PropertyData>();
		props.add(new PropertyData(testPropDef.getName(), "propTitle", "propValue", null));
		testEntity.setProperties(props);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() throws Exception {
		dao.save(testEntity);
		TestEntity e = new TestEntity(testEntity.getId());
		e._setLocaleId(testEntity.getLocaleId());
		e._setCreatorId(testEntity.getCreatorId());
		e._setRev(testEntity.getRev());
		e.setTitle(testEntity.getTitle());
		e._setRevCreatorId(testEntity.getRevCreatorId());
		dao.get(e);
		assertEquals(testEntity, e);
	}
	
	// TODO add a little more testing (e.g. revision handling, with/without units)
}

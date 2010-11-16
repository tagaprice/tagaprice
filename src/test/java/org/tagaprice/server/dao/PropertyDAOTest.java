package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.EntityDAOTest.TestEntity;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.data.Account;
import org.tagaprice.shared.data.Property;
import org.tagaprice.shared.data.PropertyTypeDefinition;
import org.tagaprice.shared.data.PropertyTypeDefinition.Datatype;

public class PropertyDAOTest {
	private TestEntity testEntity;
	private PropertyTypeDefinition testPropDef, newPropDef;
	private int localeId;
	private long uid;
	private DBConnection db;
	//private PropertyDAO dao;
	private EntityDAO dao;
	private PropertyDefinitionDAO propDefDAO;
	
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		//dao = PropertyDAO.getInstance(db);
		dao = new EntityDAO(db);
		propDefDAO = new PropertyDefinitionDAO(db);
		
		localeId = new LocaleDAO(db).get("English").getId();
		Account a = new Account("propertyTestAccount", localeId, null, null);
		new AccountDAO(db).save(a);
		uid = a.getId();
		
		testPropDef = new PropertyTypeDefinition("testProperty", "Test Property", localeId, uid, Datatype.DOUBLE, null, null, null, true);
		propDefDAO.save(testPropDef);
		newPropDef = new PropertyTypeDefinition("newProperty", "New Test Property", localeId, uid, Datatype.INT, null, null, null, true);
		propDefDAO.save(newPropDef);
		
		testEntity = new TestEntity("Title", localeId, uid);
		
		SearchResult<Property> props = new SearchResult<Property>();
		props.add(new Property(testPropDef.getName(), "propTitle", "propValue", null));
		testEntity.setProperties(props);
		
		dao.save(testEntity);
	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testPropertyId() throws Exception {
		assertNotNull("PropertyId was null after a call to save()", testEntity.getProperties().get(0).getId());
	}
	
	@Test
	public void testCreate() throws Exception {
		TestEntity e = new TestEntity(testEntity.getId());
		dao.get(e);
		assertEquals(testEntity, e);
	}
	
	@Test
	public void testRev() throws Exception {
		testEntity.getProperties().add(new Property(newPropDef.getName(), "new test property", "foobar", null));
		dao.save(testEntity);
		
		assertEquals(2, testEntity.getRev());
		
		TestEntity e1, e2;
		
		e1 = new TestEntity(testEntity.getId(), 1);
		dao.get(e1);
		e2 = new TestEntity(testEntity.getId(), 2);
		dao.get(e2);
		
		assertEquals(1, e1.getRev());
		assertEquals(2, e1.getProperties().size());
		
		assertEquals(testEntity, e2);
	}
	
	@Test
	public void testValueUpdate() throws Exception {
		testEntity.getProperties().get(0).setValue("new value");
		dao.save(testEntity);
		
		TestEntity newEntity = new TestEntity(testEntity.getId());
		dao.get(newEntity);
		
		assertEquals(testEntity, newEntity);
		
	}
}

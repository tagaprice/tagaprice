package org.tagaprice.server.dao;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.data.Type;

public class TypeDAOTest {

	private TypeDAO dao;
	private DBConnection db;
	
	@Before
	public void setUp() throws Exception{
		db = new EntityDAOTest.TestDBConnection();
		dao = new TypeDAO(db);
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}
	
	@Test
	public void testCreate() throws Exception {
		
		
	}
	
	
	@Test
	public void testRoot() throws Exception {
		//Test Root
		Type type = new Type(13);
		dao.get(type);
		assertEquals(type.getTitle(),"root");
		assertEquals(type.getSuperType(),null);
	}
	
	
	@Test
	public void testNotRoot() throws Exception {
		//Test Root
		Type type = new Type(15);
		dao.get(type);
		assertEquals(type.getTitle(),"green");
		assertEquals(type.getSuperType().getTitle(),"red");
		assertEquals(type.getSuperType().getSuperType().getTitle(),"root");
	}
	
	@Test
	public void testChildType() throws Exception {
		Type type = new Type(13);
		dao.getTypeList(type);
		assertEquals(dao.getTypeList(type).get(0).getTitle(), "red");		
	}
}

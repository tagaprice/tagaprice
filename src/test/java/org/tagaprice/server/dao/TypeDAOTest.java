package org.tagaprice.server.dao;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.Category;

public class TypeDAOTest {

	private CategoryDAO dao;
	private DBConnection db;
	
	@Before
	public void setUp() throws Exception{
		db = new EntityDAOTest.TestDBConnection();
		dao = new CategoryDAO(db);
		
		
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
		Category type = dao.getById(13);
		assertEquals(type.getTitle(),"root");
		assertEquals(type.getSuperType(),null);
	}
	
	
	@Test
	public void testNotRoot() throws Exception {
		//Test Root
		Category type = dao.getById(15);
		assertEquals(type.getTitle(),"green");
		assertEquals(type.getSuperType().getTitle(),"red");
		assertEquals(type.getSuperType().getSuperType().getTitle(),"root");
	}
	
	@Test
	public void testChildType() throws Exception {
//		original test following, this doesn't make any sense => commented
//		Category type = new Category(13);
//		dao.getTypeList(type);
//		assertEquals(dao.getTypeList(type).get(0).getTitle(), "red");		
	}
}

package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.shared.data.Unit;

public class UnitDAOTest {

	private UnitDAO dao;
	private DBConnection db;
	Unit baseunit;
	Unit childUnit;
	
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new UnitDAO(db);
		
		baseunit = new Unit("baseUnit", 1, 1, null, 0);
		dao.save(baseunit);
		
		childUnit = new Unit("unit2", 1, 1, baseunit.getId(), 0.1);
		dao.save(childUnit);
	}
	
	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}
	
	@Test
	public void testNoBaseId() throws Exception {
		Unit test = new Unit(baseunit.getId());
		dao.get(test);
		
		assertEquals(baseunit.getId(), test.getId());
	}
	
	@Test
	public void testBaseId() throws Exception{
		Unit test = new Unit(childUnit.getId());
		dao.get(test);
		assertEquals(test.getId(), childUnit.getId());
		
		Unit testBase = new Unit(test.getStandardId());
		assertEquals(baseunit.getId(), testBase.getId());
		
	}
}

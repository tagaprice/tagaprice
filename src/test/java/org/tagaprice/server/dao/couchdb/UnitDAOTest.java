package org.tagaprice.server.dao.couchdb;


import static org.junit.Assert.*;

import org.junit.Test;
import org.tagaprice.shared.entities.Unit;

public class UnitDAOTest extends AbstractDAOTest {
	CouchDBDaoFactory daoFactory = new CouchDBDaoFactory("test_");
	UnitDAO unitDAO = daoFactory.getUnitDAO();

	public UnitDAOTest() {
		addDAOClass(unitDAO);
	}
	
	@Test
	public void testCreateUnit() {
		Unit unit = new Unit("unitName");
		
		unitDAO.create(unit);
		assertNotNull(unit.getId());
		assertNotNull(unit.getRevision());

		Unit fetchedUnit = unitDAO.get(unit.getId());

		assertEquals(unit, fetchedUnit);
		
	}
}

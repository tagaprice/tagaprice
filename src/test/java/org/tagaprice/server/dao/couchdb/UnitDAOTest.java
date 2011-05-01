package org.tagaprice.server.dao.couchdb;


import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class UnitDAOTest extends AbstractDAOTest {
	public UnitDAOTest() throws IOException {
		super();
	}

	CouchDBDaoFactory daoFactory = new CouchDBDaoFactory();
	IUnitDAO unitDAO = daoFactory.getUnitDAO();
	
	@Test
	public void testCreateUnit() throws DaoException {
		Unit unit = new Unit("unitName");
		
		unitDAO.create(unit);
		assertNotNull(unit.getId());
		assertNotNull(unit.getRevision());

		Unit fetchedUnit = unitDAO.get(unit.getId());

		assertEquals(unit, fetchedUnit);
		
	}
}

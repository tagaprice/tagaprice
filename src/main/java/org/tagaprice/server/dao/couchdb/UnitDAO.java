package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;

public class UnitDAO extends DAOClass<Unit> implements IUnitDAO {

	public UnitDAO(String dbPrefix) {
		super(dbPrefix, Unit.class, "unit");
		// TODO Auto-generated constructor stub
	}

}

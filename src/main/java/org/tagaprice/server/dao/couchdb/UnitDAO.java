package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;

public class UnitDAO extends DaoClass<Unit> implements IUnitDAO {

	public UnitDAO(CouchDBDaoFactory daoFactory) {
		super(Unit.class, "unit", daoFactory._getEntityDao());
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Unit> factorizedUnits(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor) {
		// TODO Auto-generated method stub

	}

}

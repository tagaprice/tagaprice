package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;

public class UnitDAO extends DAOClass<Unit> implements IUnitDAO {

	public UnitDAO(String dbPrefix) {
		super(dbPrefix, Unit.class, "unit");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Unit create(Unit entity) {
		// TODO Auto-generated method stub
		return super.create(entity);
	}

	@Override
	public Unit update(Unit entity) {
		// TODO Auto-generated method stub
		return super.update(entity);
	}

	@Override
	public Unit get(String id) {
		// TODO Auto-generated method stub
		return super.get(id);
	}

	@Override
	public Unit get(String id, String revision) {
		// TODO Auto-generated method stub
		return super.get(id, revision);
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

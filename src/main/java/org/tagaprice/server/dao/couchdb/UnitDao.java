package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Unit;

import com.allen_sauer.gwt.log.client.Log;

public class UnitDao extends DaoClass<Unit> implements IUnitDao {

	public UnitDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Unit.class, "unit", daoFactory._getEntityDao());
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Unit> factorizedUnits(String id) {
		Log.debug("get FactorizedUnit: "+id);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor) {
		Log.debug("set FactorizedUnit: Unit: "+unit+", factorizedUnit: "+factorizedUnit+", factor: "+factor);
		// TODO Auto-generated method stub

	}

}

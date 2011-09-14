package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class UnitDao extends DaoClass<Unit> implements IUnitDao {

	public UnitDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Unit.class, Document.Type.UNIT, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Unit> factorizedUnits(String parentId) throws DaoException {
		Log.debug("get getSimilar: "+parentId);
		List<Unit> rc = new ArrayList<Unit>();

		ViewResult<?> result;


		if(parentId!=null)result = m_db.queryView("unit/similar", Unit.class, new Options().key(parentId), null);
		else result = m_db.queryView("unit/all", Unit.class, null, null);

		for (ValueRow<?> row: result.getRows()) {
			Unit unit = get(row.getId());
			rc.add(unit);
		}


		return rc;
	}

	@Override
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor) {
		Log.debug("set FactorizedUnit: Unit: "+unit+", factorizedUnit: "+factorizedUnit+", factor: "+factor);
		// TODO Auto-generated method stub

	}

}

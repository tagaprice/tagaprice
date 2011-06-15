package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.unitmanagement.IUnitService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnitServiceImpl extends RemoteServiceServlet implements IUnitService {
	private static final long serialVersionUID = 1L;

	IUnitDao unitDAO;

	public UnitServiceImpl() {
		Log.debug("Load servlet...");
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		unitDAO = daoFactory.getUnitDao();
	}

	@Override
	public Unit getUnit(String id) throws DaoException {
		return unitDAO.get(id);
	}

	@Override
	public Unit getUnit(String id, String revision) throws DaoException {
		return unitDAO.get(id, revision);
	}

	@Override
	public List<Unit> getFactorizedUnits(String id) {
		return unitDAO.factorizedUnits(id);
	}




}

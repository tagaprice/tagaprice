package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.unitmanagement.IUnitService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UnitServiceImpl extends RemoteServiceServlet implements IUnitService {
	private static final long serialVersionUID = 1L;
	MyLogger logger = LoggerFactory.getLogger(UnitServiceImpl.class);

	IUnitDAO unitDAO;

	public UnitServiceImpl() {
		logger.log("Load servlet...");
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		unitDAO = daoFactory.getUnitDAO();
	}

	@Override
	public Unit getUnit(String id) {
		return unitDAO.get(id);
	}

	@Override
	public Unit getUnit(String id, String revision) {
		return unitDAO.get(id, revision);
	}

	@Override
	public List<Unit> getRelatedUnits(String id) {
		return unitDAO.relatedUnits(id);
	}

}

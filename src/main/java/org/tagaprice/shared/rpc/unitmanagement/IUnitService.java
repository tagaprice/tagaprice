package org.tagaprice.shared.rpc.unitmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("unitservice")
public interface IUnitService extends RemoteService {

	public Unit getUnit(String id) throws DaoException;
	public Unit getUnit(String id, String revision) throws DaoException;
	public List<Unit> getFactorizedUnits(String id);
}

package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IUnitDao extends IDaoClass<Unit> {

	@Deprecated
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor);

	public List<Unit> factorizedUnits(String id)throws DaoException;
}

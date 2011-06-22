package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IUnitDao extends IDaoClass<Unit> {

	@Deprecated
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor);

	/**
	 * Returns similar Unit, or all if id = NULL
	 * @param id This must be the parent id not the unit it
	 * @return
	 * @throws DaoException
	 */
	public List<Unit> factorizedUnits(String parentId)throws DaoException;
}

package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.DAOException;

public interface IUnitDAO {

	/**
	 * Retrieves units similar to the one indicated by given unit id, i.e. the base unit or the unit itself.
	 * @param unitId Id of unit to find similar units for.
	 * @return All units similar to unit indicated by given unitId. Never null, can be empty.
	 * @throws DAOException
	 */
	SearchResult<Unit> getSimilar(long unitId) throws DAOException;

	/**
	 * Saves given unit to storage. If successful given unit's revision will be set to the revision in the database.
	 * @param unit Unit to save.
	 * @return If successful, returns the actually saved revision of given unit, i.e. it's revision will be set. Otherwise returns null.
	 * @throws DAOException
	 */
	Unit save(Unit unit) throws DAOException;

	/**
	 * Retrieves the unit indicated by given id from storage. 
	 * @param unitId Id of unit to get.
	 * @return Unit indicated by given id or null if no unit could be found.
	 * @throws DAOException
	 */
	Unit getById(long unitId) throws DAOException;

}

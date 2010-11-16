package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.exception.DAOException;

public interface IPropertyDAO {

	/**
	 * Uses given entity to retrieve a entity with properties set. 
	 * @param entity Entity to get information from. Given entity's id and revision must be set.
	 * @return Entity with equal to the one given, besides that the one returned has its properties set.
	 * @throws DAOException
	 */
	<T extends Entity> T setProperties(T entity) throws DAOException;

	/**
	 * Save given entity to storage.
	 * @param entity Entity to be saved. It's id must be set and revision must be set.
	 * @return True if entity could be saved, false otherwise.
	 * @throws DAOException
	 */
	boolean save(Entity entity) throws DAOException;
}
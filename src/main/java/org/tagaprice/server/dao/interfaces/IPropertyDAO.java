package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.exception.DAOException;

public interface IPropertyDAO {

	/**
	 * Uses given entity to retrieve a entity with properties set. 
	 * @param entity Entity to get information from. Given entity's id and revision must be set.
	 * @return Entity equal to the one given, besides that the one returned has its properties set.
	 * @throws DAOException
	 */
//	<T extends Entity> T getPropertiesByIdAndRef(T entity) throws DAOException;

	/**
	 * Saves properties of given entity to storage. If an old version of given entity exists, its 
	 * @param property Property to be saved. It's id must be set and revision must be set.
	 * @return If successful, returns the actually saved revision of given property, i.e. it's revision will be set. Otherwise returns null.
	 * @throws DAOException
	 */
	boolean saveProperties(Entity property) throws DAOException;

	SearchResult<PropertyData> getPropertiesByIdAndRef(long id, int rev)
			throws DAOException;
}
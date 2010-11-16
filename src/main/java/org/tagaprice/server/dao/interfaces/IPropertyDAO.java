package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.exception.DAOException;

public interface IPropertyDAO {

	
//	<T extends Entity> T getPropertiesByIdAndRef(T entity) throws DAOException;

	/**
	 * Saves properties of given entity to storage. If an old version of given entity exists, its 
	 * @param property Property to be saved. It's id must be set and revision must be set.
	 * @return If successful, returns the actually saved revision of given property, i.e. it's revision will be set. Otherwise returns null.
	 * @throws DAOException
	 */
	boolean saveProperties(Entity property) throws DAOException;

	/**
	 * Retrieves properties for given entityId and entityRev. 
	 * @param entityId Id of entity to find properties for.
	 * @param entityRev Id of entity to find properties for.
	 * @return Returns the properties for given entityId and entityRev, can be empty but never null.
	 * @throws DAOException
	 */
	SearchResult<PropertyData> getPropertiesByIdAndRef(long entityId, int entityRev) throws DAOException;
}
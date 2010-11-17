package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.exception.DAOException;

public interface IEntityDAO {

	/**
	 * Retrieves the entity indicated by given id.
	 * @param entity Entity object to save retrieved information to. However do not use the reference to this argument use the return value instead. 
	 * This argument exists due to performance issues since reflection would be too expensive.
	 * @param id The id to identify the entity to retrieve.
	 * @return Returns the entity indicated by given id or null if no entity could be found.
	 * @throws DAOException
	 */
	<T extends Entity> T getById(T entity, long id) throws DAOException;

	/**
	 * Retrieves the entity indicated by given id and given revision.
	 * @param entity Entity object to save retrieved information to. However do not use the reference to this argument use the return value instead. This argument exists due to performance issues since reflection would be too expensive.
	 * @param id Id to identify the entity to retrieve.
	 * @param rev Revision of entity to retrieve, must be greater or equal than 0.
	 * @return Returns the entity indicated by given id or null if no entity could be found.
	 * @throws DAOException
	 */
	<T extends Entity> T getByIdAndRev(T entity, long id, long rev) throws DAOException;

	/**
	 * Saves given entity to storage. If the entity does not exist, a new entity will be created, otherwise a new revision of the entity will be created. 
	 * The revision in given entity will be set to the revision set in the database.
	 * @param entity Entity to save. If the given entity does not provide an id the revision must be 0. If an id is provided the revision must match the current revision.
	 * @return True if entity could be saved successfully. False if not.
	 * @throws DAOException 
	 * 
	 */
	boolean save(Entity entity) throws DAOException;

}

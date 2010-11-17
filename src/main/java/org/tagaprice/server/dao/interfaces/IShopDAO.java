package org.tagaprice.server.dao.interfaces;

import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.exception.DAOException;

public interface IShopDAO {

	/**
	 * Retrieves shop indicated by given id.
	 * @param id Id of shop to retrieve.
	 * @return Shop indicated by given id or null if no shop could be found. 
	 * @throws DAOException
	 */
	Shop getById(long id) throws DAOException;

	/**
	 * Saves given shop to storage. If successful given shop's revision will be set to the revision in the database.
	 * @param shop Shop to be saved to storage. If the given shop does not provide an id the revision must be 0. If an id is provided the revision must match the current revision.
	 * @return True if shop could be saved, false if not.
	 * @throws DAOException
	 */
	boolean save(Shop shop) throws DAOException;

	/**
	 * Retrieves shop indicated by given id and given revision.
	 * @param id Id to identify the shop to retrieve.
	 * @param rev Revision of shop to retrieve, must be greater or equal than 0.
	 * @return Returns the shop indicated by given id or null if no shop could be found.
	 * @throws DAOException
	 */
	Shop getByIdAndRef(long id, long rev) throws DAOException;

}

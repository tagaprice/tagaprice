package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.shared.entities.Receipt;
import org.tagaprice.shared.exception.DAOException;

public interface IReceiptDAO {

	/**
	 * Saves given receipt to storage. If successful given receipt's revision will be set to the revision in the database.
	 * @param receipt Receipt to be saved to storage. If the given receipt does not provide an id the revision must be 0. If an id is provided the revision must match the current revision.
	 * @return If successful, returns the actually saved revision of given receipt, i.e. it's revision will be set. Otherwise returns null.
	 * @throws DAOException
	 */
	Receipt save(Receipt receipt) throws DAOException;

	/**
	 * Retrieves receipt indicated by given id.
	 * @param id Id of receipt to retrieve.
	 * @return Receipt indicated by given id or null if no receipt could be found. 
	 * @throws DAOException
	 */
	Receipt getById(long id) throws DAOException;

	/**
	 * Retrieves receipts created by user indicated by given id.
	 * @param id Id of user to retrieve receipts for.
	 * @return Returns the list of user's receipts, can be empty but never null.
	 * @throws DAOException
	 */
	List<Receipt> getUserReceipts(long id) throws DAOException;

}

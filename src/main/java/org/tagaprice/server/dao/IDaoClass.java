package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IDaoClass<T extends Document> {
	public T create(final T document) throws DaoException;
	public T get(String id) throws DaoException;
	public T get(String id, String revision) throws DaoException;
	public T update(final T document) throws DaoException;
	public void delete(Document ... documents) throws DaoException;
	
	/**
	 * Faster alternative to get() that doesn't fetch child objects (for DAO implementations that support this)
	 * 
	 * @param id Document ID
	 * @param revision Document revision (use null if you want the current revision)
	 * @return Requested Document
	 * @throws DaoException If anything went wrong
	 */
	T getOnly(String id, String revision) throws DaoException;

	/**
	 * Faster alternative to get() that doesn't fetch child objects (for DAO implementations that support this)
	 * 
	 * @param id Document ID
	 * @return Requested Document
	 * @throws DaoException If anything went wrong
	 */
	T getOnly(String id) throws DaoException;
}

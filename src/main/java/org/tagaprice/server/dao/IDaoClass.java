package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IDaoClass<T extends Document> {
	public T create(final T document) throws DaoException;
	public T get(String id) throws DaoException;
	public T get(String id, String revision) throws DaoException;
	public T update(final T document) throws DaoException;
	public void delete(T document) throws DaoException;

}

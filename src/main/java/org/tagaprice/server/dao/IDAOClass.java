package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IDAOClass<T extends ASimpleEntity> {
	public T create(final T entity);
	public T get(String id) throws DaoException;
	public T get(String id, String revision) throws DaoException;
	public T update(final T entity);
	public void delete(T entity);

}

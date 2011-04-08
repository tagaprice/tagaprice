package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.ASEntity;

public interface IDAOClass<T extends ASEntity> {
	public T create(final T entity);
	public T get(String id);
	public T get(String id, String revision);
	public T update(final T entity);
	public void delete(T entity);

}

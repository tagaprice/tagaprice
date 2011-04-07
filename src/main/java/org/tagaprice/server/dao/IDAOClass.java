package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.ISEntity;

public interface IDAOClass<T extends ISEntity> {
	public T create(final T entity);
	public T get(IRevisionId id);
	public T update(final T entity);
	public void delete(T entity);

}

package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.ICategory;

public interface ICategoryDAO {
	public ICategory create(final ICategory category);
	public ICategory get(IRevisionId id);
	public ICategory update(final ICategory product);
	public void delete(ICategory product);
	public List<ICategory> find(final ICategory searchPattern);
	public List<ICategory> list();
}

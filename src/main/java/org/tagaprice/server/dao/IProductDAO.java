package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.productmanagement.IProduct;

public interface IProductDAO {
	public IProduct create(final IProduct product);
	public IProduct get(IRevisionId id);
	public IProduct update(final IProduct product);
	public void delete(IProduct product);
	public List<IProduct> find(final IProduct searchPattern);
}

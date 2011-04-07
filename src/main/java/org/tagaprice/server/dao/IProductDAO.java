package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.IProduct;

public interface IProductDAO extends IDAOClass<IProduct> {
	public List<IProduct> find(final IProduct searchPattern);
}

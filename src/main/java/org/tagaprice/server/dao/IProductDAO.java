package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.Product;

public interface IProductDAO extends IDAOClass<Product> {
	public List<Product> find(final Product searchPattern);
}

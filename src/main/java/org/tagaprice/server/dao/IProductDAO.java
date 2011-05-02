package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IProductDAO extends IDaoClass<Product> {
	public List<Product> find(final Product searchPattern) throws DaoException;
}

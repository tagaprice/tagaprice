package org.tagaprice.server.dao;

import java.util.List;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IProductDao extends IDaoClass<Product> {
	public List<Product> find(String searchPattern) throws DaoException;
	
	@Deprecated
	public List<Product> list() throws DaoException;
}

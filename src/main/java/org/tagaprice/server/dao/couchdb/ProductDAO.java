package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.shared.entities.productmanagement.IProduct;

public class ProductDAO extends DAOClass<IProduct> implements IProductDAO {
	public ProductDAO() {
		super(IProduct.class, "product");
	}

	@Override
	public List<IProduct> find(IProduct searchPattern) {
		throw new UnsupportedOperationException("ProductDAO.find() wasn't implemented yet");
	}
}

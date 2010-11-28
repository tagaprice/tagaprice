package org.tagaprice.server.service;

import org.tagaprice.core.beans.Product;
import org.tagaprice.server.dao.ints.IProductDAO;

public interface IProductManagement {

	Product save(Product input);

	void setProductDAO(IProductDAO mockedDao);

}

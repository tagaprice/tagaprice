package org.tagaprice.server.service;

import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.dao.interfaces.IProductDAO;

public class DefaultProductService implements IProductService {
	//	private static BeanFactory factory = new XmlBeanFactory(new FileInputStream("hello.xml"));
	private IProductDAO _productDao;

	@Override
	public Product save(Product product) {
		return _productDao.save(product);
	}

	public void setProductDAO(IProductDAO productDao) {
		_productDao = productDao;
	}
}

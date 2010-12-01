package org.tagaprice.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.tagaprice.core.beans.Product;
import org.tagaprice.server.dao.ints.IProductDAO;

public class DefaultProductService implements IProductService {
	//	private static BeanFactory factory = new XmlBeanFactory(new FileInputStream("hello.xml"));
	@Autowired
	private IProductDAO _productDao;

	@Override
	public Product save(Product product) {
		return _productDao.save(product);
	}

	public void setProductDAO(IProductDAO productDao) {
		_productDao = productDao;
	}
}

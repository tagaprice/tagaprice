package org.tagaprice.server.service;

import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;

public class DefaultProductService implements IProductService {
	//	private static BeanFactory factory = new XmlBeanFactory(new FileInputStream("hello.xml"));
	private IProductDAO _productDao;

	@Override
	public ProductRevision save(ProductRevision product) {
		//		TODO fix return _productDao.save(new Product());
		return new ProductRevision();
	}

	public void setProductDAO(IProductDAO productDao) {
		_productDao = productDao;
	}
}

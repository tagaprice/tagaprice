package org.tagaprice.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.api.IllegalRevisionException;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;

public class DefaultProductService implements IProductService {
	//	private static BeanFactory factory = new XmlBeanFactory(new FileInputStream("hello.xml"));
	private IProductDAO _productDao;
	private Logger _log = LoggerFactory.getLogger(DefaultProductService.class);

	/**
	 * Attempts to save given product and returns the actually saved product with all its revisions.
	 * Throws an IllegalRevisionException if given product's highest (i.e. newest) revision is older than the highest revision already saved.
	 * @throws IllegalRevisionException
	 */
	@Override
	public Product save(Product product) throws IllegalRevisionException {
		Long id = product.getId();

		if(id == null) { //new product
			Long newId = IdCounter.getNewId();
			product.setId(newId);
		} else {
			Product persistedProduct = _productDao.getById(id);
			ProductRevision persistedHighestRevision = persistedProduct.getCurrentRevision();
			Integer persistedRevisionNumber = persistedHighestRevision.getRevisionNumber();

			ProductRevision detachedHighestRevision = product.getCurrentRevision();
			Integer detachedRevisionNumber = detachedHighestRevision.getRevisionNumber();

			if(persistedRevisionNumber > detachedRevisionNumber) //more than one revision has been saved meanwhile
			{
				String message = "attempted to save outdated revision. highest persisted revision number: "
					+ persistedRevisionNumber
					+ ", highest revision number to be saved: "
					+ detachedRevisionNumber;
				_log.debug(message);
				throw new IllegalRevisionException(message);
			}
			else if (persistedRevisionNumber == detachedRevisionNumber) //one revision has been saved meanwhile
			{
				if(!persistedHighestRevision.fullEquals(detachedHighestRevision))
				{
					String message = "attempted to save outdated revision (revisions are not equal). highest persisted revision number: "
						+ persistedRevisionNumber
						+ ", highest revision number to be saved: "
						+ detachedRevisionNumber;
					_log.debug(message);
					throw new IllegalRevisionException(message);
				}
			}
		}

		_productDao.save(product);
		return product;
	}

	public void setProductDAO(IProductDAO productDao) {
		_productDao = productDao;
	}
}

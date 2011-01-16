package org.tagaprice.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;

@Transactional
public class DefaultProductService implements IProductService {
	//	private static BeanFactory factory = new XmlBeanFactory(new FileInputStream("hello.xml"));
	private IProductDAO _productDao;
	private Logger _log = LoggerFactory.getLogger(DefaultProductService.class);

	@Override
	public Product save(Product product) throws OutdatedRevisionException {
		if(product==null)
			throw new IllegalArgumentException("product must not be null");

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
				_log.info(message);
				throw new OutdatedRevisionException(message);
			}
			else if (persistedRevisionNumber == detachedRevisionNumber) //one revision has been saved meanwhile
			{
				if(!persistedHighestRevision.equals(detachedHighestRevision))
				{
					String message = "attempted to save outdated revision (revisions are not equal). highest persisted revision number: "
						+ persistedRevisionNumber
						+ ", highest revision number to be saved: "
						+ detachedRevisionNumber;
					_log.info(message);
					throw new OutdatedRevisionException(message);
				}
			}
		}

		// TODO this doesn't load the current product. it just saves and returns it. fix here or in dao?
		// TODO check if product has at least one ProductRevision. check here or in dao and throw?
		product = _productDao.save(product);
		return product;
	}

	public void setProductDAO(IProductDAO productDao) {
		_productDao = productDao;
	}
}

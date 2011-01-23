package org.tagaprice.server.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.entities.ArgumentUtitlity;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;

public class DefaultProductService implements IProductService {
	private IProductDAO _productDao;
	private Logger _log = LoggerFactory.getLogger(DefaultProductService.class);
	private IProductRevisionDAO _productRevisionDao;


	@Transactional
	@Override
	public Product save(Product product) throws OutdatedRevisionException {
		if (product == null)
			throw new IllegalArgumentException("product must not be null");
		if(product.getCurrentRevision().getRevisionNumber() == null) {
			_log.warn("revisionNumber of productRevision is NULL");
			throw new OutdatedRevisionException("revisionNumber must not be null");
		}

		Long id = product.getId();

		if (id == null) { // new product
			Long newId = IdCounter.getNewId();
			product.setId(newId);
		} else {
			Product persistedProduct = _productDao.getById(id);
			ProductRevision persistedHighestRevision = persistedProduct.getCurrentRevision();
			Integer persistedRevisionNumber = persistedHighestRevision.getRevisionNumber();

			ProductRevision detachedHighestRevision = product.getCurrentRevision();
			Integer detachedRevisionNumber = detachedHighestRevision.getRevisionNumber();

			if (persistedRevisionNumber > detachedRevisionNumber) // more than one revision has been saved meanwhile
			{
				String message = "attempted to save outdated revision. highest persisted revision number: "
					+ persistedRevisionNumber + ", highest revision number to be saved: " + detachedRevisionNumber;
				_log.info(message);
				throw new OutdatedRevisionException(message);
			} else if (persistedRevisionNumber == detachedRevisionNumber) // one revision has been saved meanwhile
			{
				if (!persistedHighestRevision.equals(detachedHighestRevision)) {
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

	@Transactional(readOnly=true)
	@Override
	public List<Product> getByTitle(String title) {
		if (title == null)
			throw new IllegalArgumentException("title must not be null");
		_log.debug("title " + title);

		List<ProductRevision> revisions = _productRevisionDao.getByTitle(title);
		_log.debug("number of revisions found:" + revisions.size());


		HashSet<Long> ids = new HashSet<Long>();
		for (ProductRevision revision : revisions) {
			ids.add(revision.getId());
		}

		_log.debug("number of different product ids found:" + ids.size());


		List<Product> products = new ArrayList<Product>();

		for (Long id : ids) {
			products.add(_productDao.getById(id));
		}

		// WORKAROUND for hibernate being lazy
		for (Product p : products) {
			p.getRevisions();
			p.getCurrentRevision();
			p.getId();
		}

		_log.debug("number of product found:" + ids.size());

		return products;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Product> getAll() {
		return _productDao.getAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Product getById(Long id) {
		ArgumentUtitlity.checkNull("id", id);
		return _productDao.getById(id);
	}

	public void setProductDAO(IProductDAO productDao) {
		_log.debug("productDao set to " + productDao);
		_productDao = productDao;
	}

	public void setProductRevisionDAO(IProductRevisionDAO productRevisionDao) {
		_productRevisionDao = productRevisionDao;
	}
}

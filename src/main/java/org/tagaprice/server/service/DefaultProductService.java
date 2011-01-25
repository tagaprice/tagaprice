package org.tagaprice.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IProductService;
import org.tagaprice.core.api.OutdatedRevisionException;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.api.UserNotLoggedInException;
import org.tagaprice.core.entities.Account;
import org.tagaprice.core.entities.ArgumentUtitlity;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.core.entities.Session;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;

public class DefaultProductService implements IProductService {
	private IProductDAO _productDao;
	private Logger _log = LoggerFactory.getLogger(DefaultProductService.class);
	private IProductRevisionDAO _productRevisionDao;
	private SessionService _sessionFactory;


	@Transactional
	@Override
	public Product save(Product newProduct, Session session) throws UserNotLoggedInException, OutdatedRevisionException {
		ArgumentUtitlity.checkNull("session", session);
		ArgumentUtitlity.checkNull("newProduct", newProduct);
		
		@SuppressWarnings("unused")
		Account creator; //TODO use this creator to compare new productrevisions
		if((creator = _sessionFactory.getAccount(session)) == null) {
			throw new UserNotLoggedInException("You need to login to create a new product.");
		}
			
		
		if(newProduct.getCurrentRevision().getRevisionNumber() == null) {
			_log.warn("revisionNumber of productRevision is NULL");
			throw new OutdatedRevisionException("revisionNumber must not be null");
		}

		Long id = newProduct.getId();

		if (id != null) { //product already persisted
			Product persistedProduct = _productDao.getById(id);
			ProductRevision persistedHighestRevision = persistedProduct.getCurrentRevision();
			Integer persistedHighestRevisionNumber = persistedHighestRevision.getRevisionNumber();

			ProductRevision newHighestRevision = newProduct.getCurrentRevision();
			Integer newHighestRevisionNumber = newHighestRevision.getRevisionNumber();
			
			int numberNewRevisions = newHighestRevisionNumber - persistedHighestRevisionNumber;

			if (numberNewRevisions < 0) {// more than one revision has been saved meanwhile
				String message = "attempted to save outdated revision. highest persisted revision number: "
					+ persistedHighestRevisionNumber + ", highest revision number to be saved: " + newHighestRevisionNumber;
				_log.info(message);
				throw new OutdatedRevisionException(message);
			} 
			
			if (numberNewRevisions == 0 && !persistedHighestRevision.equals(newHighestRevision)) { // one different revision has been saved meanwhile
				String message = "attempted to save outdated revision (revisions are not equal). highest persisted revision number: "
					+ persistedHighestRevisionNumber
					+ ", highest revision number to be saved: "
					+ newHighestRevisionNumber;
				_log.info(message);
				throw new OutdatedRevisionException(message);
			}
			
			//TODO START WORKAROUND hibernate will throw exception if trying to save with already fetched category/account id : part1
			HashMap<Long, Account> creators = new HashMap<Long, Account>();
			HashMap<Long, Category> categories = new HashMap<Long, Category>();
			for(ProductRevision revision : persistedProduct.getRevisions()) {
				creators.put(revision.getCreator().getUid(), revision.getCreator());
				categories.put(revision.getCategory().getId(), revision.getCategory());
				
				Category curCategory = revision.getCategory().getParent();
				while(curCategory != null) {
					categories.put(curCategory.getId(), curCategory);
					creators.put(curCategory.getCreator().getUid(), curCategory.getCreator());
					
					curCategory = curCategory.getParent();
				}
			}
			
			//END WORKAROUND : part1

			
			Iterator<ProductRevision> it = newProduct.getRevisions().iterator();
			for(;numberNewRevisions>0; numberNewRevisions--) {
				ProductRevision next = it.next();
				
				//TODO START WORKAROUND hibernate will throw exception if trying to save with already fetched category/account id : part2
				if(creators.containsKey(next.getCreator().getUid()))
					next.setCreator(creators.get(next.getCreator().getUid()));
				
				
				
				Category curCategory = next.getCategory();
				if(categories.containsKey(curCategory.getId()))
					next.setCategory(categories.get(curCategory.getId()));
				if(creators.containsKey(curCategory.getCreator().getUid()))
					curCategory.setCreator(creators.get(curCategory.getCreator().getUid()));
				
				
				Category childCategory = curCategory;
				Category rootCategory = curCategory.getParent();
				while(rootCategory != null) {
					if(categories.containsKey(rootCategory.getId())) {
						childCategory.setParent(categories.get(rootCategory.getId()));
						rootCategory = null;
					} else if(creators.containsKey(rootCategory.getCreator().getUid())) {
						rootCategory.setCreator(creators.get(rootCategory.getCreator().getUid()));
						
						childCategory = rootCategory;
						rootCategory = childCategory.getParent();
					}
					
				}
				
				//END WORKAROUND : part2
				
				
				persistedProduct.addRevision(next);
			}
			
			newProduct = persistedProduct;
		}

		// TODO this doesn't load the current product. it just saves and returns it. fix here or in dao?
		// TODO check if product has at least one ProductRevision. check here or in dao and throw?
		_log.debug("id of the product to save: " + newProduct.getId());
		newProduct = _productDao.save(newProduct);
		return newProduct;
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
	
	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}
}

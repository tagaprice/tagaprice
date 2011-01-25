package org.tagaprice.server.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IShopService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.ArgumentUtitlity;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;

public class DefaultShopService implements IShopService {
	IShopDAO _shopDao;
	SessionService _sessionFactory;

	@Transactional(readOnly=true)
	@Override
	public Shop getById(long id) throws ServerException {
		return _shopDao.getById(id);
	}

	@Transactional
	@Override
	public Shop save(Shop shop) throws ServerException {
		ArgumentUtitlity.checkNull("shop", shop);
		return _shopDao.save(shop);
	}

	@Transactional(readOnly=true)
	@Override
	public List<BasicShop> getByTitle(String title) throws ServerException {
		ArgumentUtitlity.checkNull("title", title);
		return _shopDao.getByTitle(title);
	}

	@Transactional(readOnly=true)
	@Override
	public List<BasicShop> getByTitleFuzzy(String title) throws ServerException {
		ArgumentUtitlity.checkNull("title", title);
		return _shopDao.getByTitleFuzzy(title);
	}

	@Transactional(readOnly=true)
	@Override
	public List<BasicShop> getAll() throws ServerException {
		return _shopDao.getAll();
	}

	//
	// bean helper methods
	//

	public void setShopDAO(IShopDAO shopDao) throws ServerException {
		_shopDao = shopDao;
	}

	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}

}

package org.tagaprice.server.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.tagaprice.core.api.IShopService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;

@Transactional
public class DefaultShopService implements IShopService {
	IShopDAO _shopDao;
	SessionService _sessionFactory;

	@Override
	public Shop getById(long id) throws ServerException {
		// TODO what happens if id doesn't exist?
		return _shopDao.getById(id);
	}

	@Override
	public Shop save(Shop shop) throws ServerException {
		// TODO replace this by ArgumentUtility
		if(shop == null)
			throw new IllegalArgumentException("shop is null");
		return _shopDao.save(shop);
	}

	@Override
	public List<BasicShop> getByTitle(String title) throws ServerException {
		return _shopDao.getByTitle(title);
	}

	@Override
	public List<BasicShop> getByTitleFuzzy(String title) throws ServerException {
		return _shopDao.getByTitleFuzzy(title);
	}

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

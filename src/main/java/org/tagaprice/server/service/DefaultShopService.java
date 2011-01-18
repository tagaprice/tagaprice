package org.tagaprice.server.service;

import java.util.List;

import org.tagaprice.core.api.IShopService;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;

public class DefaultShopService implements IShopService {
	private IShopDAO _serviceDAO;

	@Override
	public Shop getById(Long id) {
		return _serviceDAO.getById(id);
	}
	
	@Override
	public List<Shop> getByTitle(String title) {
		return _serviceDAO.getByTitle(title);
	}

	public void setServiceDAO(IShopDAO serviceDAO) {
		_serviceDAO = serviceDAO;
	}
}

package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.shopmanagement.*;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {
	private static final long serialVersionUID = 1L;

	ISessionDao sessionDAO;
	IShopDao shopDAO;

	public ShopServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		Log.debug("Load servlet...");

		sessionDAO = daoFactory.getSessionDao();
		shopDAO = daoFactory.getShopDao();

	}

	@Override
	public List<Shop> getShops(Shop searchCriteria) throws DaoException {
		Log.debug("getShops with Shop SearchCriteria ");
		if (searchCriteria != null) {
			return shopDAO.find(searchCriteria.getTitle());
		}
		else return shopDAO.list();
	}

	@Override
	public Shop getShop(String id, String revision) throws DaoException {
		Log.debug("getShop with id " + id+", rev "+revision);
		return shopDAO.get(id, revision);
	}

	@Override
	public Shop getShop(String id) throws DaoException {
		Log.debug("getShop with id " + id);
		return shopDAO.get(id);
	}

	@Override
	public Shop saveShop(String sessionId, Shop shop) throws DaoException, UserNotLoggedInException {
		Log.debug("save Shop " + shop);

		if (sessionId == null) throw new UserNotLoggedInException("Can't save a shop without having a valid session!");
		Session session = sessionDAO.get(sessionId);
		Shop rc = null;

		// TODO check session validity
		shop.setCreatorId(session.getCreatorId());

		if (shop.getId() != null) {
			rc = shopDAO.update(shop);
		}
		else {
			rc = shopDAO.create(shop);
		}
		return rc;
	}




}
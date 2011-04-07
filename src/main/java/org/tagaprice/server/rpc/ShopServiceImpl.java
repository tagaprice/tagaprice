package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.*;
import org.tagaprice.shared.rpc.shopmanagement.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {
	private static final long serialVersionUID = -4954618872880443049L;
	MyLogger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	IShopDAO shopDAO;

	public ShopServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		logger.log("Load ShopServiceImpl servlet...");

		shopDAO = daoFactory.getShopDAO();

	}

	@Override
	public List<Shop> getShops(Shop searchCriteria) {
		logger.log("getShops with Shop SearchCriteria ");

		return shopDAO.list();
	}

	@Override
	public Shop getShop(String id, String revision) {
		logger.log("getShop with id " + id+", rev "+revision);
		return shopDAO.get(id, revision);
	}
	
	@Override
	public Shop getShop(String id) {
		logger.log("getShop with id " + id);
		return shopDAO.get(id);
	}

	@Override
	public Shop saveShop(Shop shop) {
		logger.log("save Shop " + shop);
		Shop rc = null;
		if (shop.getId() != null) {
			rc = shopDAO.update(shop);
		}
		else {
			rc = shopDAO.create(shop);
		}
		return rc;
	}




}

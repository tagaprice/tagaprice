package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.IShopService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4954618872880443049L;
	MyLogger _logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	@Override
	public ArrayList<IShop> getShops(IShop searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IShop getShop(IRevisionId revisionId) {
		_logger.log("getShop with RevId " + revisionId);
		IShop shop = new Shop(new RevisionId(1L), "shop", "street", "1120", "Vienna", Country.at, 0.32, 23.42);

		_logger.log("send Shop: " + shop);

		return shop;
	}

	@Override
	public IShop save(IShop shop) {
		_logger.log("saveShop with Shop " + shop);
		shop.setRevisionId(new RevisionId(shop.getRevisionId().getId(), shop.getRevisionId().getRevision() + 1));

		_logger.log("send Shop back: " + shop);
		return shop;
	}

}

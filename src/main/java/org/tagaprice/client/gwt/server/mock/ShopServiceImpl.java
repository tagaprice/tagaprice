package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4954618872880443049L;
	MyLogger _logger = LoggerFactory.getLogger(ShopServiceImpl.class);


	public ShopServiceImpl() {
		_logger.log("Load servlet...");
	}

	@Override
	public ArrayList<IShop> getShops(IShop searchCriteria) {
		// TODO Auto-generated method stub


		_logger.log("getShops with IShop SearchCriteria ");

		ArrayList<IAddress> myAddresses = new ArrayList<IAddress>();
		myAddresses.add(new Address(new RevisionId(1L, 1), "street", "1120", "Vienna", Country.at, 48.20888815483086, 16.371173858642578));

		IShop shop = new Shop(new RevisionId(1L, 1), "shop", "street", "1120", "Vienna", Country.at, 48.20888815483086, 16.371173858642578, myAddresses);

		_logger.log("send Shop: " + shop);

		ArrayList<IShop> result = new ArrayList<IShop>();
		result.add(shop);

		return result;
	}

	@Override
	public IShop getShop(IRevisionId revisionId) {
		_logger.log("getShop with RevId " + revisionId);

		ArrayList<IAddress> myAddresses = new ArrayList<IAddress>();
		myAddresses.add(new Address(new RevisionId(1L), "street", "1120", "Vienna", Country.at, 48.20888815483086, 16.371173858642578));

		IShop shop = new Shop(new RevisionId(1L, 1), "shop", "street", "1120", "Vienna", Country.at, 48.20888815483086, 16.371173858642578, myAddresses);

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

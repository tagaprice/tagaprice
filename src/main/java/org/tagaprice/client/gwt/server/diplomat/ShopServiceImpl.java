package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagaprice.client.gwt.server.diplomat.converter.ShopConverter;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.IShopService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.boot.Boot;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7276616784431789501L;
	Logger _logger = LoggerFactory.getLogger(ShopServiceImpl.class);
	org.tagaprice.core.api.IShopService _coreShopService;

	public ShopServiceImpl() {
		String service = "defaultShopService";
		_logger.debug("Attempting to load "+service+" from core.api");
		_coreShopService = (org.tagaprice.core.api.IShopService) Boot.getApplicationContext().getBean(service);
		_logger.debug("Loaded "+service+" successfully.");
	}

	@Override
	public ArrayList<IShop> getShops(IShop searchCriteria) {
		_logger.debug("getShops with searchCriteria: " + searchCriteria);
		ShopConverter converter = ShopConverter.getInstance();
		ArrayList<IShop> gwtShops = new ArrayList<IShop>();
		List<BasicShop> coreShops = null;
		try {
			if(searchCriteria != null) {
				coreShops = _coreShopService.getByTitleFuzzy(searchCriteria.getTitle());
			} else {
				coreShops = _coreShopService.getByTitleFuzzy("");
			}
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(coreShops != null) {
			for(BasicShop bs: coreShops) {
				gwtShops.add(converter.convertCoreBasicShopToGWTShop(bs));
			}
		}


		return gwtShops;
	}

	@Override
	public IShop getShop(IRevisionId revisionId) {
		_logger.debug("getShop with RevId " + revisionId);
		ShopConverter converter = ShopConverter.getInstance();
		org.tagaprice.core.entities.Shop coreShop = null;
		try {
			coreShop = _coreShopService.getById(revisionId.getId());
		} catch (ServerException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		_logger.debug("got CoreShop: " + coreShop);
		IShop shop =converter.convertCoreShopToGWTShop(coreShop);

		_logger.debug("converted GWTShop: " + shop);

		return shop;
	}

	@Override
	public IShop save(IShop shop)  {
		_logger.debug("saveShop with Shop " + shop);

		ShopConverter shopConverter =ShopConverter.getInstance();

		try {
			Shop shopCore = shopConverter.convertGWTShopToCoreShop(shop);
			shopCore = this._coreShopService.save(shopCore);
			return shopConverter.convertCoreShopToGWTShop(shopCore);

		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//shop.setRevisionId(new RevisionId(shop.getRevisionId().getId(), shop.getRevisionId().getRevision() + 1));

		_logger.debug("send Shop back: " + shop);
		return shop;
	}

}

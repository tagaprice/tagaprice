package org.tagaprice.client.gwt.server.diplomat.converter;


import java.util.Set;
import org.slf4j.*;

import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;

public class ShopConverter {



	private final static ShopConverter converter = new ShopConverter();
	Logger _log = LoggerFactory.getLogger(ShopConverter.class);

	public static ShopConverter getInstance() {
		return ShopConverter.converter;
	}

	/**
	 * Converts a coreShop to a gwtShop
	 * @param coreShop
	 * @return
	 */

	public IShop convertCoreShopToGWT(final Shop coreShop){

		long id = coreShop.getId();
		String title = coreShop.getTitle();
		double latitude = coreShop.getLatitude();
		double longitude = coreShop.getLongitude();
		String street = "";
		String zip = "";
		String city = "";
		Country country = null;

		IShop shopGWT = new org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop(title, street,zip,city,country,latitude,longitude);

		return shopGWT;

	}

	/**
	 * Converts a gwtShop to a coreShop
	 * @param shopGWT
	 * @return
	 */

	public Shop convertGWTShoptoCore(final IShop shopGWT){
		_log.debug("Convert gwtShop to coreShop,id:"+ shopGWT.getRevisionId());

		Long shopId = null;
		if(shopGWT.getRevisionId()!= null){
			if(shopGWT.getRevisionId().getId() !=0L){
				shopId = shopGWT.getRevisionId().getId();
			}
		}
		String title = shopGWT.getTitle();
		double latitude = shopGWT.getLat();
		double longitude = shopGWT.getLng();
		Set<ReceiptEntry> receiptEntries = null;


		Shop coreShop = new Shop(shopId, title, latitude, longitude, receiptEntries);

		_log.debug("Converted:"+ shopGWT + "into"+ coreShop);
		return coreShop;

	}
}

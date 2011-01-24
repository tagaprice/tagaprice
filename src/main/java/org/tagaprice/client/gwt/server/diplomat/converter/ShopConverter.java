package org.tagaprice.client.gwt.server.diplomat.converter;


import java.util.Set;

import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;

public class ShopConverter {



	private final static ShopConverter converter = new ShopConverter();


	public static ShopConverter getInstance() {
		return ShopConverter.converter;
	}



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

	public Shop convertGWTShoptoCore(final IShop shopGWT){


		Set<ReceiptEntry> receiptEntries = null;

		return null;

	}
}

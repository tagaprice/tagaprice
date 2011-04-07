package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is just a wrapper!
 *
 */
public class ShopSelecter extends Composite implements IShopSelecter {

	IShopSelecter shopSelecter = GWT.create(IShopSelecter.class);

	public ShopSelecter(){
		initWidget(shopSelecter.asWidget());

	}
	@Override
	public Shop getShop() {
		return shopSelecter.getShop();
	}

	@Override
	public void setShop(Shop shop) {
		shopSelecter.setShop(shop);

	}
	@Override
	public void setAvailableShops(ArrayList<Shop> shops) {
		shopSelecter.setAvailableShops(shops);

	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return shopSelecter.addChangeHandler(handler);
	}

}

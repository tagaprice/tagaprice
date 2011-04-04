package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.shopmanagement.IShop;

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
	public IShop getShop() {
		return shopSelecter.getShop();
	}

	@Override
	public void setShop(IShop shop) {
		shopSelecter.setShop(shop);

	}
	@Override
	public void setAvailableShops(ArrayList<IShop> shops) {
		shopSelecter.setAvailableShops(shops);

	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return shopSelecter.addChangeHandler(handler);
	}

}

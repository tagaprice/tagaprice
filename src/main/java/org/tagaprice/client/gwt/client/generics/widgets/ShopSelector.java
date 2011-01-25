package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.ui.Composite;


public class ShopSelector extends Composite implements IShopSelector {

	//IShopSelector shopSelecter = GWT.create(IShopSelector.class);

	public ShopSelector(){
		//initWidget(shopSelecter.asWidget());

	}
	@Override
	public IShop getShop() {
		return null; //shopSelecter.getShop();
	}

	@Override
	public void setShop(IShop shop) {
		//shopSelecter.setShop(shop);

	}

}

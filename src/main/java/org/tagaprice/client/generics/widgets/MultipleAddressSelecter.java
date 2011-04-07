package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class MultipleAddressSelecter extends Composite implements IMultipleAddressSelecter {

	private IMultipleAddressSelecter addressSeleter = GWT.create(IMultipleAddressSelecter.class);

	public MultipleAddressSelecter() {
		initWidget(addressSeleter.asWidget());
	}

	@Override
	public void setShops(ArrayList<Shop> address) {
		addressSeleter.setShops(address);
	}

	@Override
	public ArrayList<Shop> getShops() {
		return addressSeleter.getShops();
	}

	@Override
	public void setCurrentAddress(Address address) {
		addressSeleter.setCurrentAddress(address);
	}

	@Override
	public void addShop(Shop shop) {
		addressSeleter.addShop(shop);
	}

}

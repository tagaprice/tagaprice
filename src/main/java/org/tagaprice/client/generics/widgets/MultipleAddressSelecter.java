package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class MultipleAddressSelecter extends Composite implements IMultipleAddressSelecter {

	private IMultipleAddressSelecter addressSeleter = GWT.create(IMultipleAddressSelecter.class);

	public MultipleAddressSelecter() {
		initWidget(addressSeleter.asWidget());
	}

	@Override
	public void setShop(ArrayList<IShop> address) {
		addressSeleter.setShop(address);
	}

	@Override
	public ArrayList<IShop> getShop() {
		return addressSeleter.getShop();
	}

	@Override
	public void setCurrentAddress(Address address) {
		addressSeleter.setCurrentAddress(address);
	}

}

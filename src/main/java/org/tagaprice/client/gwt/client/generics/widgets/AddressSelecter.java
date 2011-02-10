package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class AddressSelecter extends Composite implements IAddressSelecter {

	private IAddressSelecter addressSeleter = GWT.create(IAddressSelecter.class);

	public AddressSelecter() {
		initWidget(addressSeleter.asWidget());
	}

	@Override
	public void setAddresses(ArrayList<IAddress> address) {
		addressSeleter.setAddresses(address);
	}

	@Override
	public ArrayList<IAddress> getAddresses() {
		return addressSeleter.getAddresses();
	}

}

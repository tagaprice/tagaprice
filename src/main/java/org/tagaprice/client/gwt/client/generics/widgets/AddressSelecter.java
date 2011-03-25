package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.shared.entities.Address;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is a wrapper class that implements the {@link IAddressSelecter} for every screen.
 * With this wrapper it is possible to change the screen and use the classes in a
 * {@link com.google.gwt.uibinder.client.UiBinder} or create new instances.
 * 
 */
public class AddressSelecter extends Composite implements IAddressSelecter {

	IAddressSelecter addressSelecter = GWT.create(IAddressSelecter.class);

	public AddressSelecter() {
		initWidget(addressSelecter.asWidget());
	}

	@Override
	public void setAddress(ISubsidiary address) {
		addressSelecter.setAddress(address);
	}

	@Override
	public ISubsidiary getAddress() {
		return addressSelecter.getAddress();
	}

	@Override
	public void setCurrentAddress(Address address) {
		addressSelecter.setCurrentAddress(address);
	}

}

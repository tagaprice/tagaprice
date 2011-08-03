package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Address;
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
	public void setAddress(Address address) {
		addressSelecter.setAddress(address);
	}

	@Override
	public Address getAddress() {
		return addressSelecter.getAddress();
	}

	@Override
	public void setReadOnly(boolean read) {
		addressSelecter.setReadOnly(read);
	}

	@Override
	public boolean isReadOnly() {
		return addressSelecter.isReadOnly();
	}


}

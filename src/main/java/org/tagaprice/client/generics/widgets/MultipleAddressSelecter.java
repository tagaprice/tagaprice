package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class MultipleAddressSelecter extends Composite implements IMultipleAddressSelecter {

	private IMultipleAddressSelecter addressSeleter = GWT.create(IMultipleAddressSelecter.class);

	public MultipleAddressSelecter() {
		initWidget(addressSeleter.asWidget());
	}

	@Override
	public void setSubsidiaries(ArrayList<ISubsidiary> address) {
		addressSeleter.setSubsidiaries(address);
	}

	@Override
	public ArrayList<ISubsidiary> getSubsidiaries() {
		return addressSeleter.getSubsidiaries();
	}

	@Override
	public void setCurrentAddress(Address address) {
		addressSeleter.setCurrentAddress(address);
	}

}

package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.user.client.ui.IsWidget;

public interface IMultipleAddressSelecter extends IsWidget {

	/**
	 * Set some {@link IAddress} to the {@link IAddressSeleter}
	 * @param address that will be added to the {@link IAddressSeleter}
	 */
	public void setAddresses(ArrayList<IAddress> address);

	/**
	 * Returns all {@link IAddress} includes in the {@link IAddressSeleter}
	 * @return all {@link IAddress} includes in the {@link IAddressSeleter}
	 */
	public ArrayList<IAddress> getAddresses();
}

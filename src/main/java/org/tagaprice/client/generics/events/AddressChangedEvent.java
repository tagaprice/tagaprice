package org.tagaprice.client.generics.events;

import org.tagaprice.shared.entities.Address;

import com.google.gwt.event.shared.GwtEvent;

public class AddressChangedEvent extends GwtEvent<AddressChangedEventHandler> {

	public static Type<AddressChangedEventHandler> TYPE = new Type<AddressChangedEventHandler>();

	private final Address _address;

	public AddressChangedEvent(Address address) {
		_address = address;
	}


	/**
	 * @return the address
	 */
	public Address getAddress() {
		return _address;
	}



	@Override
	protected void dispatch(AddressChangedEventHandler handler) {
		handler.onAddressChanged(this);
	}

	@Override
	public Type<AddressChangedEventHandler> getAssociatedType() {
		return AddressChangedEvent.TYPE;
	}

}

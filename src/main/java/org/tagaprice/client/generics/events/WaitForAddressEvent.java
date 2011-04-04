package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.GwtEvent;

public class WaitForAddressEvent extends GwtEvent<WaitForAddressEventHandler> {

	public static Type<WaitForAddressEventHandler> TYPE = new Type<WaitForAddressEventHandler>();


	public WaitForAddressEvent() {
	}


	@Override
	protected void dispatch(WaitForAddressEventHandler handler) {
		handler.onAddressChanged(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WaitForAddressEventHandler> getAssociatedType() {
		return WaitForAddressEvent.TYPE;
	}

}

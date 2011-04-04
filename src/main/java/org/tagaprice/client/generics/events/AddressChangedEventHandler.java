package org.tagaprice.client.generics.events;

import com.google.gwt.event.shared.EventHandler;

public interface AddressChangedEventHandler extends EventHandler {
	void onAddressChanged(AddressChangedEvent event);
}

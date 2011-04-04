package org.tagaprice.client.generics.events;

import com.google.gwt.event.shared.EventHandler;

public interface WaitForAddressEventHandler extends EventHandler {
	void onAddressChanged(WaitForAddressEvent event);
}

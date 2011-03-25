package org.tagaprice.client.gwt.client.generics.events;

import com.google.gwt.event.shared.EventHandler;

public interface InfoBoxEventHandler extends EventHandler {
	void onNewInfo(InfoBoxShowEvent event);
	void onDestroyInfo(InfoBoxDestroyEvent event);
}

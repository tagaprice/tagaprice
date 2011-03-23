package org.tagaprice.client.gwt.client.generics.widgets;

import org.tagaprice.client.gwt.client.generics.events.InfoBoxEvent;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface defines the primary methods a InfoBox has.
 *
 */
public interface IInfoBox extends IsWidget {

	/**
	 * Display the {@link InfoBoxEvent} in a box.
	 * @param event {@link InfoBoxEvent}
	 */
	public void addInfoBoxEvent(InfoBoxEvent event);
}

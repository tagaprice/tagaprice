package org.tagaprice.client.generics.widgets;

import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;

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
	public void addInfoBoxEvent(InfoBoxShowEvent event);

	/**
	 * Defines what should be removed.
	 * @param event destroy options
	 */
	public void removeInfoBoxEvent(InfoBoxDestroyEvent event);
}

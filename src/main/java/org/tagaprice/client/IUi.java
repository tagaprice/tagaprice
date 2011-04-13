package org.tagaprice.client;

import org.tagaprice.client.generics.widgets.InfoBox;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.user.client.ui.IsWidget;

public interface IUi {

	/**
	 * Returns the screen ui widget
	 * @param activityManager is responsible for ui change
	 * @param clientFactory
	 * @return screen ui widget
	 */
	public IsWidget getUI(ActivityManager activityManager, ClientFactory clientFactory);

	/**
	 * Returns the current infoBox
	 * @return the info box
	 */
	public InfoBox getInfoBox();
}

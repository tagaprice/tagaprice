package org.tagaprice.client.features;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Every View must implement this Interface.
 * Extend Widget with more methods
 */
public interface IFeatureView extends IsWidget {

	/**
	 * Reset the view. That it looks like a new view.
	 */
	public void reset();

}

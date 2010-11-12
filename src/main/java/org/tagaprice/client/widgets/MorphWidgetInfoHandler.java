package org.tagaprice.client.widgets;

import org.tagaprice.shared.PropertyDefinition.Datatype;

/**
 * Is called if the value of the MorphWidget has changed, and calls the specific
 * function (empty, error, success)
 * 
 */
public interface MorphWidgetInfoHandler {

	/**
	 * Is called if the MorphWidget is empty after changing.
	 */
	public void onEmpty();

	/**
	 * Is called if the MorphWidget value has not the expected type after
	 * changing.
	 * 
	 * @param infoType
	 *            the expected type
	 */
	public void onError(Datatype infoType);

	/**
	 * Is called if the MorphWidget value has the expected type after changing.
	 * 
	 * @param infoType
	 *            the expected type
	 */
	public void onSuccess(Datatype infoType);
}

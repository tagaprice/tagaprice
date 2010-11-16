package org.tagaprice.client.propertyhandler;

/**
 * This Handle is called if a property has changed.
 *
 */
public interface IPropertyChangeHandler {
	
	/**
	 * Is called if the change was not successful.
	 */
	public void onError();
	
	/**
	 * Is called if the change was successful.
	 */
	public void onSuccess();
}

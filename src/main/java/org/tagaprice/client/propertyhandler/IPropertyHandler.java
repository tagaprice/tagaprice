package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;

/**
 * Defines the main methods for all PropertyHandler.
 * 
 */
public interface IPropertyHandler {

	/**
	 * Implement a change handler that is called if a property has changed.
	 * 
	 * @param handler
	 *            is called if a property has changed.
	 */
	public void addChangeHandler(PropertyChangeHandler handler);

	/**
	 * Returns the current implemented change handler that is called if a
	 * property has changed.
	 * 
	 * @return returns the current implemented change handler.
	 */
	public PropertyChangeHandler getPropertyChangeHandler();

	/**
	 * Returns the PropertyGroup of an handler which contains the definitions of
	 * the group members.
	 * 
	 * @return returns the PropertyGroup of the handler.
	 */
	public PropertyGroup getPropertyGroup();

	/**
	 * Returns all Properties hold by an handler. If one property will change
	 * the {@link #getPropertyChangeHandler()} will be called.
	 * 
	 * @return returns all properties hold by the handler.
	 */
	public HashMap<String, ArrayList<PropertyData>> getPropertyList();
}

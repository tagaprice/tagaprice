package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.shared.entities.Property;
import org.tagaprice.shared.entities.PropertyGroup;

import com.google.gwt.user.client.ui.Composite;

/**
 * The APropertyHandler implements all standard methods for the propertyHandler.
 * 
 */
abstract class APropertyHandler extends Composite implements IPropertyHandler {

	private HashMap<String, ArrayList<Property>> _hashProperties;
	private PropertyGroup _propGroup;
	private IPropertyChangeHandler _handler;

	/**
	 * The standard constructor to create a new propertyHandler (e.g.
	 * NutritionFood). If a property is used by this handler the flag
	 * <b>read</b> in {@link Property#getRead()} is set TRUE.
	 * 
	 * @param hashProperties
	 *            the currently known properties
	 * @param propGroup
	 *            holds all properties of an actual property
	 * @param handler
	 *            implements the handler that will be called if a property
	 *            change.
	 */
	public APropertyHandler(
			HashMap<String, ArrayList<Property>> hashProperties,
			PropertyGroup propGroup, IPropertyChangeHandler handler) {
		this._hashProperties = hashProperties;
		this._propGroup = propGroup;
		this._handler = handler;
	}

	@Override
	public void addChangeHandler(IPropertyChangeHandler handler) {
		this._handler = handler;
	}

	@Override
	public PropertyGroup getPropertyGroup() {
		return _propGroup;
	};

	@Override
	public IPropertyChangeHandler getPropertyChangeHandler() {
		return _handler;
	}

	@Override
	public HashMap<String, ArrayList<Property>> getPropertyList() {
		return _hashProperties;
	}
}

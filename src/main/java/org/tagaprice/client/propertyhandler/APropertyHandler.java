package org.tagaprice.client.propertyhandler;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;

import com.google.gwt.user.client.ui.Composite;

abstract class APropertyHandler extends Composite implements IPropertyHandler{

	private HashMap<String, ArrayList<PropertyData>> _hashProperties;
	private PropertyGroup _propGroup;
	private PropertyChangeHandler _handler;
	
	public APropertyHandler(HashMap<String, ArrayList<PropertyData>> hashProperties, PropertyGroup propGroup, PropertyChangeHandler handler) {
		this._hashProperties=hashProperties;
		this._propGroup = propGroup;
		this._handler=handler;
	}
	
	@Override
	public void addChangeHandler(PropertyChangeHandler handler){
		this._handler=handler;
	}
	
	@Override
	public PropertyGroup getPropertyGroup(){
		return _propGroup;
	};

	
	@Override
	public PropertyChangeHandler getPropertyChangeHandler(){
		return _handler;
	}
	
	@Override
	public HashMap<String, ArrayList<PropertyData>> getPropertyList(){
		return _hashProperties;
	}
}

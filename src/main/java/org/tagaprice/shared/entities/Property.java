/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PropertyData.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;


/**
 * Holds information about a concrete property.
 */
public class Property implements ISerializable {
	private static final long serialVersionUID = 1L;

	private Long _id;
	private String _name;
	private String _value;
	private String _title;
	private Unit _unit;
	private boolean _read = false;
	
	/**
	 * Default Constructor needed for serialization.
	 */
	public Property() { }
	
	/**
	 * Constructor for creating a new {@link Property} (without id).
	 * @param name name of this {@link Property}, unique in a given locale
	 * @param title descriptive, localized title of this {@link Property} 
	 * @param value value of this {@link Property}
	 * @param unit {@link Unit} of the value of this {@link Property}
	 */
	public Property (String name, String title, String value, Unit unit) {
		setName(name);
		setTitle(title);
		setValue(value);
		setUnit(unit);
	}
	
	/**
	 * Constructor for creating a {@link Property} with given id.
	 * @param id unique ID of this {@link Property}
	 * @param name name of this {@link Property}, unique in a given locale
	 * @param title descriptive, localized title of this {@link Property}
	 * @param value value of this {@link Property}
	 * @param unit {@link Unit} of the value of this {@link Property}
	 */
	public Property(
			long id,
			String name,
			String title,
			String value,
			Unit unit) {
		this._id = id;
		setName(name);
		setTitle(title);
		setValue(value);
		setUnit(unit);
	}
	
	/**
	 * @return ID of this {@link Property}.
	 */
	public Long getId() {
		return _id;
	}
	
	/**
	 * @return true, if the ID of this {@link Property} is set.
	 */
	public boolean hasId() {
		return _id != null;
	}
	
	/**
	 * @param id ID to set.
	 */
	public void _setId(Long id) {
		this._id = id;
	}
	
	/**
	 * @return the name of this {@link Property}, unique in a given locale
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * @param name name of this {@link Property}, unique in a given locale
	 */
	public void setName(String name) {
		this._name = name;
	}
	
	/**
	 * @return descriptive, localized title of this {@link Property}
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * 
	 * @param title descriptive, localized title of this {@link Property}
	 */
	public void setTitle(String title) {
		this._title = title;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		
		return _value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this._value = value;
	}
	
	/**
	 * @return the {@link Unit} of this {@link Property}.
	 */
	public Unit getUnit() {
		return _unit;
	}
	
	/**
	 * @return true, if the unit of this {@link Property} is set.
	 */
	public boolean hasUnit() {
		return _unit != null;
	}
	
	/**
	 * @param unit the {@link Unit} of this {@link Property}.
	 */
	public void setUnit(Unit unit) {
		this._unit = unit;
	}
	
	/**
	 * TODO refactor this functionality (mark if property is already displayed in the GUI)
	 * @param read set the mark if this property is already displayed in the GUI
	 */
	public void setRead(boolean read){
		this._read = read;
	}
	
	/**
	 * @return TODO refactor this (true, if this property is already displayed in the GUI)
	 */
	public boolean getRead(){
		return _read;
	}

	@Override
	public String getSerializeName() {
		return "property";
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof Property) {
			Property p = (Property) o;
			if (!Entity._compare(_id, p._id)) rc = false;
			if (!Entity._compare(getName(), p.getName())) rc = false;
			if (!Entity._compare(getValue(), p.getValue())) rc = false;
			if (!Entity._compare(getUnit(), p.getUnit())) rc = false;
		}
		else rc = false;
		return rc;
	}
	
	@Override
	public String toString() {
		return "PropertyData {\n" +
				"id: " + getId() +
				"\nname: " + getName() +
				"\nvalue: " + getValue() +
				"\nunit: "+getUnit() +
			"\n}\n";
	}
}

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
 * 
 */
public class Property implements ISerializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String value;
	private String title;
	private Unit unit;
	private boolean read=false;
	
	public Property() {
		// TODO Auto-generated constructor stub
	}
	
	public Property (String name, String title, String value, Unit unit) {
		setName(name);
		setTitle(title);
		setValue(value);
		setUnit(unit);
	}
	
	public Property(
			long id,
			String name,
			String title,
			String value,
			Unit unit) {
		this.id = id;
		setName(name);
		setTitle(title);
		setValue(value);
		setUnit(unit);
	}
	
	public Long getId() {
		return id;
	}
	
	public boolean hasId() {
		return id != null;
	}
	
	public void _setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the unitId
	 */
	public Unit getUnit() {
		return unit;
	}
	
	public boolean hasUnit() {
		return unit != null;
	}
	
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * Increments the readCount. 
	 */
	public void setRead(boolean read){
		this.read=read;
	}
	
	public boolean getRead(){
		return read;
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
			if (!Entity._compare(id, p.id)) rc = false;
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

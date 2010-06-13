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
package org.tagaprice.shared;


/**
 * 
 */
public class PropertyData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String title;
	private String value;
	private Unit unit;
	private boolean read=false;
	
	public PropertyData() {
		// TODO Auto-generated constructor stub
	}
	
	public PropertyData(
			String name,
			String title,
			String value,
			Unit unit) {
		setName(name);
		setTitle(title);
		setValue(value);
		setUnit(unit);
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
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

		if (o instanceof PropertyData) {
			PropertyData p = (PropertyData) o;
			if (getName() == null) {
				if (p.getName() != null) rc = false;
			}
			else if (!getName().equals(p.getName())) rc = false;
			
			if (getValue() == null) {
				if (p.getValue() != null) rc = false;
			}
			else if (!getValue().equals(p.getValue())) rc = false;
			
			if (getTitle() == null) {
				if (p.getTitle() != null) rc = false;
			}
			else if (!getTitle().equals(p.getTitle())) rc = false;
			
			if (getUnit() == null) {
				if (p.getUnit() != null) rc = false; 
			}
			else if (!getUnit().equals(p.getUnit())) rc = false;
		}
		else rc = false;
		return rc;
	}
}

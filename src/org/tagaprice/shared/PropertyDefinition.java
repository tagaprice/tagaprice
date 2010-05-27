/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: TypeProperty.java
 * Date: May 20, 2010
*/
package org.tagaprice.shared;

/**
 * Describes a property
 * **/
public class PropertyDefinition implements Entity{
	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String title;
	public enum Datatype {
	    STRING, DOUBLE, INT	
	}
	public Datatype type;
	private int minValue;
	private int maxValue;
	private Unit unit;
	private boolean unique;
	
	public PropertyDefinition() {
		// TODO Auto-generated constructor stub
	}
	
	public PropertyDefinition(long id, String name, String title, Datatype type, int minValue, int maxValue, Unit unit, boolean unique)
	{ 	this(id, name, title, type, unit, unique);
  		this.minValue = minValue;
  		this.maxValue = maxValue;
		
	}
	
	public PropertyDefinition(long id, String name, String title, Datatype type, Unit unit, boolean unique){
		this.id = id;
		this.name=name;
		this.title=title;
		this.type = type;
		this.unit=unit;
		this.unique = unique;
		maxValue=Integer.MAX_VALUE;
		minValue=Integer.MIN_VALUE;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Datatype getType() {
		return type;
	}

	public void setType(Datatype type) {
		this.type = type;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public String getSerializeName() {
		// TODO Auto-generated method stub
		return "propertyDefinition";
	}
	
	
	
}

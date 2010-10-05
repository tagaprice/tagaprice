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
public class PropertyDefinition extends Entity {
	private static final long serialVersionUID = 1L;

	public enum Datatype implements Serializable {
	    STRING, DOUBLE, INT;

	    public String getSerializeName() {
		    return "propertyDatatype";
	    }
	}

	private String name;
	public Datatype type;
	private Integer minValue;
	private Integer maxValue;
	private Unit unit;
	private boolean unique;
	
	/**
	 * default constructor (used by GWT's serialization)
	 */
	public PropertyDefinition() {
		super();
	}
	
	/**
	 * constructor used for querying the current revision of a PropertyDefinition (using PropertyDefinitionDAO) 
	 * @param id PropertyDefinition ID
	 */
	public PropertyDefinition(long id) {
		super(id);
	}
	
	/**
	 * constructor used for querying a specific revision of a PropertyDefinition (using PropertyDefinitionDAO)
	 * @param id PropertyDefinition ID
	 * @param rev PropertyDefinition revision (has to exist)
	 */
	public PropertyDefinition(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * constructor used for creating a new PropertyDefinition (using PropertyDefinitionDAO)
	 * @param name locale-independent property name (e.g. "weight")
	 * @param title descriptive PropertyDefinition title
	 * @param localeId locale ID
	 * @param creatorId PropertyDefinition creator
	 * @param type PropertyDefinition type
	 * @param minValue minimum property value (may be null)
	 * @param maxValue maximum property value (may be null)
	 * @param unit property unit 
	 * @param unique specifies if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links) 
	 */
	public PropertyDefinition(String name, String title, int localeId, long creatorId, Datatype type, Integer minValue, Integer maxValue, Unit unit, boolean unique) {
		super(title, localeId, creatorId);
		this.name = name;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unit = unit;
		this.unique = unique;
	}
	
	/**
	 * constructor used for saving a new PropertyDefinition revision (using PropertyDefinitionDAO)
	 * @param id PropertyDefinition ID
	 * @param rev current revision (will be checked by ProperyDefinitionDAO to detect concurrent write attempts)
	 * @param name (new) locale-independent property name (e.g. "weight")
	 * @param title (new) descriptive PropertyDefinition title
	 * @param creatorId revision's creator
	 * @param type (new) PropertyDefinition type
	 * @param minValue (new) minimum property value (may be null)
	 * @param maxValue (new) maximum property value (may be null)
	 * @param unit (new) property unit 
	 * @param unique specifies if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links) 
	 */
	public PropertyDefinition(long id, int rev, String name, String title, long creatorId, Datatype type, int minValue, int maxValue, Unit unit, boolean unique) {
		super(id, rev, title, creatorId);
		this.name = name;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unit = unit;
		this.unique = unique;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public Datatype getType() {
		return type;
	}

	public void setType(Datatype type) {
		this.type = type;
	}

	public Integer getMinValue() {
		return minValue;
	}
	
	public boolean hasMinValue() {
		return minValue != null;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}
	
	public boolean hasMaxValue() {
		return maxValue != null;
	}

	public void setMaxValue(Integer maxValue) {
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
	
	
	// TODO implement missing PropertyDefinition.equals()
}

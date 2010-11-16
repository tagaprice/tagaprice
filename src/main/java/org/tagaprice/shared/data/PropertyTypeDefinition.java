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
package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

/**
 * Defines a type of property
 **/
public class PropertyTypeDefinition extends Entity {
	private static final long serialVersionUID = 1L;

	/** TODO refactor to own class ? */
	public enum Datatype implements Serializable {
	    STRING, DOUBLE, INT;

	    public String getSerializeName() {
		    return "propertyDatatype";
	    }
	}

	private String name;
	public Datatype type;
	/** has only meaning for numeric types, NULL if no numeric type */
	private Integer minValue;
	/** has only meaning for numeric types, NULL if no numeric type */
	private Integer maxValue;
	private Unit unit;
	private boolean unique;
	
	/**
	 * default constructor (used by GWT's serialization)
	 */
	public PropertyTypeDefinition() {
		super();
	}
	
	/**
	 * constructor used for querying the current revision of a PropertyDefinition
	 * @param id PropertyDefinition ID
	 */
	public PropertyTypeDefinition(long id) {
		super(id);
	}
	
	/**
	 * constructor used for querying a specific revision of a {@link PropertyTypeDefinition}
	 * @param id PropertyTypeDefinition ID
	 * @param rev PropertyTypeDefinition revision (has to exist)
	 */
	public PropertyTypeDefinition(long id, int rev) {
		super(id, rev);
	}
	
	/**
	 * onstructor used for querying a {@link PropertyTypeDefinition} object by it's name 
	 * @param name property name (e.g. "weight")
	 * @param localeId locale to query the {@link PropertyTypeDefinition} for
	 */
	public PropertyTypeDefinition(String name, int localeId) {
		super(null, localeId, null);
		this.name = name;
	}
	
	/**
	 * constructor used for creating a new {@link PropertyTypeDefinition}
	 * @param name locale-independent property name (e.g. "weight")
	 * @param title descriptive {@link PropertyTypeDefinition} title
	 * @param localeId locale ID
	 * @param creatorId {@link PropertyTypeDefinition} creator
	 * @param type {@link PropertyTypeDefinition} datatype
	 * @param minValue minimum property value (must be null if type is not numeric)
	 * @param maxValue maximum property value (must be null if type is not numeric)
	 * @param unit unit of this {@link PropertyTypeDefinition}
	 * @param unique specifies if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links) 
	 */
	public PropertyTypeDefinition(String name, String title, int localeId, long creatorId, Datatype type, Integer minValue, Integer maxValue, Unit unit, boolean unique) {
		super(title, localeId, creatorId);
		this.name = name;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unit = unit;
		this.unique = unique;
	}
	
	/**
	 * constructor used for saving a new {@link PropertyTypeDefinition} revision
	 * @param id {@link PropertyTypeDefinition} ID
	 * @param rev current revision (will be checked by ProperyDefinitionDAO to detect concurrent write attempts)
	 * @param name (new) locale-independent property name (e.g. "weight")
	 * @param title (new) descriptive PropertyDefinition title
	 * @param creatorId revision's creator
	 * @param type (new) {@link PropertyTypeDefinition} datatype
	 * @param minValue (new) minimum property value (must be null if type is not numeric)
	 * @param maxValue (new) maximum property value (must be null if type is not numeric)
	 * @param unit (new) property unit 
	 * @param unique specifies if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links) 
	 */
	public PropertyTypeDefinition(long id, int rev, String name, String title, long creatorId, Datatype type, int minValue, int maxValue, Unit unit, boolean unique) {
		super(id, rev, title, creatorId);
		this.name = name;
		this.type = type;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.unit = unit;
		this.unique = unique;
	}

	/**
	 * @return true, if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links)
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param unique specifies if a Property is unique for a Product (e.g. it's weight) or can be set several times (e.g. links)
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * @return name of this {@link PropertyTypeDefinition} (e.g. "weight")
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name name of this {@link PropertyTypeDefinition} (e.g. "weight")
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the {@link Datatype} of this {@link PropertyTypeDefinition}
	 */
	public Datatype getType() {
		return type;
	}

	/**
	 * @param type the {@link Datatype} of this {@link PropertyTypeDefinition}
	 */
	public void setType(Datatype type) {
		this.type = type;
	}

	/**
	 * @return minimum property value (must be null if type is not numeric)
	 */
	public Integer getMinValue() {
		return minValue;
	}
	
	/**
	 * @return true, if the minimum value is set
	 */
	public boolean hasMinValue() {
		return minValue != null;
	}

	/**
	 * @param minValue minimum property value (must be null if type is not numeric)
	 */
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return maximum property value (must be null if type is not numeric)
	 */
	public Integer getMaxValue() {
		return maxValue;
	}
	
	/**
	 * @return true, if the maximum value is set
	 */
	public boolean hasMaxValue() {
		return maxValue != null;
	}

	/**
	 * @param maxValue maximum property value (must be null if type is not numeric)
	 */
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return {@link Unit} of this {@link PropertyTypeDefinition}
	 */
	public Unit getUnit() {
		return unit;
	}

	/**
	 * @param unit property {@link Unit}
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public String getSerializeName() {
		return "propertyDefinition";
	}
	
	
	// TODO implement missing PropertyDefinition.equals()
}

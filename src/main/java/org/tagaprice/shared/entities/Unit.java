/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: Unit.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared.entities;

public class Unit extends Entity {
	private static final long serialVersionUID = 1L;
	
	private Long standardId = null;
	private double factor = 0;

	/**
	 * default constructor (needed for serialization)
	 */
	public Unit() {
		super();
	}
	
	/**
	 * constructor for querying a Unit's current revision (using UnitDAO)
	 * @param id Unit ID
	 */
	public Unit(long id) {
		super(id);
	}
	
	/**
	 * constructor for querying a specific Unit revision (using UnitDAO)
	 * @param id Unit ID
	 * @param rev Unit revision
	 */
	public Unit(long id, int rev) {
		super(id, rev);
	}

	/**
	 * constructor for saving a new Unit (using UnitDAO)
	 * @param title descriptive Unit name
	 * @param localeId current locale
	 * @param creatorId currently logged in user
	 * @param standardId standard (SI) Unit ID (may be null if this unit is a standard-SI)
	 * @param factor factor to calculate between the standard Unit and this one
	 */
	public Unit(String title, int localeId, long creatorId, Long standardId, double factor) {
		super(title, localeId, creatorId);
		this.standardId = standardId;
		this.factor = factor;
	}
	
	/**
	 * constructor for saving an existing Unit (using UnitDAO)
	 * @param id Unit ID
	 * @param rev current revision (will be checked by UnitDAO to detect concurrent storage attempts)
	 * @param title descriptive Unit name
	 * @param creatorId currently logged in user
	 * @param standardId standard (SI) Unit ID (may be null)
	 * @param factor factor to calculate between the standard Unit and this one
	 */
	public Unit(long id, int rev, String title, long creatorId, Long standardId, double factor) {
		super(id, rev, title, creatorId);
		this.standardId = standardId;
		this.factor = factor;
	}

	public Long getStandardId() {
		return standardId;
	}
	
	public void _setStandardId(Long standardId) {
		this.standardId = standardId;
	}
	
	public double getFactor() {
		return factor;
	}
	
	public void _setFactor(double factor) {
		this.factor = factor;
	}

	@Override
	public String getSerializeName() {
		return "unit";
	}
	
	@Override
	public boolean equals(Object o) {
		boolean rc = super.equals(o);
		
		if (rc && o instanceof Unit) {
			Unit u = (Unit) o;
			if (getFactor() != u.getFactor()) rc = false;
		}
		return rc;
	}
}

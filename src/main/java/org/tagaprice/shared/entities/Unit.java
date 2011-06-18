package org.tagaprice.shared.entities;

import org.tagaprice.shared.entities.accountmanagement.User;

/**
 * Represents Units and converts between them.
 *
 */
public class Unit extends AEntity {
	private static final long serialVersionUID = 1L;
	private double _factor = 1.0;
	private Unit _parent=null;

	public Unit() {

	}

	/**
	 * <b>NEW</b>
	 * Create new Unit
	 * @param creator Creator of the current entity revision
	 * @param title Unit title
	 */
	public Unit(User creator, String title, Unit parent, double factor) {
		this(creator, null, null, title, parent, factor);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Update or get unit from db
	 * @param creator Creator of the current entity revision
	 * @param id
	 * @param revision
	 * @param title
	 */
	public Unit(User creator, String id, String revision, String title, Unit parent, double factor) {
		super(creator, id, revision, title);
		setParent(parent);
		setFactor(factor);
	}

	/**
	 * @return the factor
	 */
	public double getFactor() {
		return _factor;
	}

	/**
	 * @param factor the factor to set
	 */
	public void setFactor(double factor) {
		_factor = factor;
	}

	public void setParent(Unit parent){
		_parent = parent;
	}

	public Unit getParent(){
		return _parent;
	}



}

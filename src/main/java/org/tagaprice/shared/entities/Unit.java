package org.tagaprice.shared.entities;

/**
 * Represents Units and converts between them.
 *
 */
public class Unit extends AEntity {
	private static final long serialVersionUID = 1L;
	private double _factor = 1.0;

	public Unit() {

	}

	/**
	 * <b>NEW</b>
	 * Create new Unit
	 * @param title unit title
	 */
	public Unit(String title) {
		this(null, null, title);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Update or get unit from db
	 * @param id
	 * @param revision
	 * @param title
	 */
	public Unit(String id, String revision, String title) {
		super(id, revision, title);
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





}

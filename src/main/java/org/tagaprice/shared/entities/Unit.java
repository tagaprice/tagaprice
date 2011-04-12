package org.tagaprice.shared.entities;

/**
 * Represents Units and converts between them.
 *
 */
public class Unit extends AEntity {
	private static final long serialVersionUID = 1L;

	public Unit(String id, String revision, String title) {
		super(id, revision, title);
	}

	public Unit(String title) {
		this(null, null, title);
	}

	public Unit() {
		
	}
}

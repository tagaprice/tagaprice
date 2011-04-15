package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Unit;

public interface IUnitDAO extends IDAOClass<Unit> {

	/**
	 * A list of all related units. e.g. g -> kg, g, mg
	 * 
	 * @param unit
	 *            Unit id for which you search related units. If it is null you get all units.
	 * @return A list of related units
	 */
	public List<Unit> relatedUnits(String id);
}

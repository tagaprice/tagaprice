package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Unit;

public interface IUnitDAO extends IDAOClass<Unit> {

	/**
	 * A list of all related units. e.g. g -> kg, g, mg
	 * 
	 * @param unit
	 *            Unit for which you search realted units. If it is null you get all units.
	 * @return A list of related units
	 */
	public List<Unit> relatedUnits(Unit unit);
}

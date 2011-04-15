package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.Unit;

public interface IUnitDAO extends IDAOClass<Unit> {

	public void setFactorizedUnit(String unit, String factorizedUnit, double factor);

	public List<Unit> factorizedUnits(String id);
}

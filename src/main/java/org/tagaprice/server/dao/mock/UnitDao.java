package org.tagaprice.server.dao.mock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;


public class UnitDao implements IUnitDao {
	MyLogger logger = LoggerFactory.getLogger(UnitDao.class);
	private Random random = new Random(1654196865);
	HashMap<String, Unit> _units = new HashMap<String, Unit>();
	HashMap<String, HashMap<String, Double>> _factoredUnits = new HashMap<String, HashMap<String,Double>>();

	public UnitDao() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Unit create(Unit unit) {
		String id = ""+random.nextInt();
		unit.setId(id);
		unit.setRevision("1");

		_units.put(id, unit);

		return unit;
	}

	@Override
	public Unit get(String id) {
		return _units.get(id);
	}

	@Override
	public Unit get(String id, String revision) {
		return _units.get(id);
	}

	@Override
	public Unit update(Unit unit) {
		unit.setRevision(""+(Integer.parseInt(unit.getRevision())+1));

		_units.put(unit.getId(), unit);

		return unit;
	}

	@Override
	public void delete(Unit entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFactorizedUnit(String unit, String factorizedUnit, double factor) {
		logger.log("set relativeUnits: Unit: "+unit + ", rel: "+factorizedUnit+", factor: "+factor);
		if(_factoredUnits.get(unit)==null)
			_factoredUnits.put(unit, new HashMap<String, Double>());

		_factoredUnits.get(unit).put(factorizedUnit, factor);

	}

	@Override
	public List<Unit> factorizedUnits(String id) {
		logger.log("relativeUnits: "+id);
		ArrayList<Unit> rc = new ArrayList<Unit>();

		if(id==null){
			for (String key:_units.keySet())
				rc.add(_units.get(key));
		}else{
			Set<String> keys = _factoredUnits.get(id).keySet();
			for (String key:keys){
				logger.log("key: "+key);

				_units.get(key).setFactor(_factoredUnits.get(id).get(key));
				rc.add(_units.get(key));
			}
		}
		return rc;
	}



}

package org.tagaprice.server.dao.mock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Unit;
import com.allen_sauer.gwt.log.client.Log;


public class UnitDao implements IUnitDao {
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
		Log.debug("set relativeUnits: Unit: "+unit + ", rel: "+factorizedUnit+", factor: "+factor);
		if(_factoredUnits.get(unit)==null)
			_factoredUnits.put(unit, new HashMap<String, Double>());

		_factoredUnits.get(unit).put(factorizedUnit, factor);

	}

	@Override
	public List<Unit> factorizedUnits(String id) {
		Log.debug("relativeUnits: "+id);
		ArrayList<Unit> rc = new ArrayList<Unit>();

		if(id==null){
			for (String key:_units.keySet())
				rc.add(_units.get(key));
		}else{
			//Add self
			String parentId=null;
			if(_units.get(id).getParent()==null){
				parentId=_units.get(id).getId();
			}else{
				parentId=_units.get(id).getParent().getId();
			}

			//add Parent
			rc.add(_units.get(parentId));


			for (String key:_units.keySet()){
				//all with parent == parentId
				if(_units.get(key).getParent()!=null && _units.get(key).getParent().getId().equals(parentId)){
					Log.debug("key: "+key);
					rc.add(_units.get(key));
				}
			}
		}

		return rc;
	}



}

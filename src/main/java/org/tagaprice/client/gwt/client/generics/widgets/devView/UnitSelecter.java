package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.IUnitSelecter;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import org.tagaprice.core.entities.Unit;

public class UnitSelecter extends Composite implements IUnitSelecter{

	ListBox _listBoxUnit = new ListBox();

	public UnitSelecter() {
		initWidget(_listBoxUnit);
		allowedUnit(null);
	}

	@Override
	public void setRelatedUnit(Unit unit) {
		allowedUnit(unit);
	}

	@Override
	public void setUnit(Unit unit) {
		int pos = 0;

		for(int i= 0; i < Unit.values().length; i++ ) {
			if(Unit.values()[i].equals(unit)) {
				pos=i;
			}
		}
		_listBoxUnit.setSelectedIndex(pos);
	}

	@Override
	public Unit getUnit() {
		return Unit.valueOf(_listBoxUnit.getItemText(_listBoxUnit.getSelectedIndex()));
	}


	/**
	 * This method will set the display part to allowed {@link IUnit}s
	 * @param unit allowed unit. If null, all are displayed.
	 */
	private void allowedUnit(Unit unit){
		_listBoxUnit.clear();

		for(Unit u: Unit.values()) {
			_listBoxUnit.addItem(u.name());
		}
	}

}

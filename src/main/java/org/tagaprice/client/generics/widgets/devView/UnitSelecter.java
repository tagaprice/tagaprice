package org.tagaprice.client.generics.widgets.devView;

import org.tagaprice.client.generics.widgets.IUnitChangedHandler;
import org.tagaprice.client.generics.widgets.IUnitSelecter;
import org.tagaprice.shared.entities.Unit;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class UnitSelecter extends Composite implements IUnitSelecter{

	private ListBox _listBoxUnit = new ListBox();
	private IUnitChangedHandler _unitChangedHandler;

	public UnitSelecter() {
		initWidget(_listBoxUnit);
		allowedUnit(null);

		//add Listener
		_listBoxUnit.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent arg0) {
				if(_unitChangedHandler!=null)_unitChangedHandler.onChange(getUnit());

			}
		});
	}

	@Override
	public void setRelatedUnit(Unit unit) {
		allowedUnit(unit);
	}

	@Override
	public void setUnit(Unit unit) {
		int pos = 0;

		/// TODO fix me
/*		for(int i= 0; i < Unit.values().length; i++ ) {
			if(Unit.values()[i].equals(unit)) {
				pos=i;
			}
		}*/
		_listBoxUnit.setSelectedIndex(pos);
	}

	@Override
	public Unit getUnit() {
		return null;
		//return Unit.valueOf(_listBoxUnit.getItemText(_listBoxUnit.getSelectedIndex()));
	}


	/**
	 * This method will set the display part to allowed {@link Unit}s
	 * @param unit allowed unit. If null, all are displayed.
	 */
	private void allowedUnit(Unit unit){
		_listBoxUnit.clear();

		/*
		if(unit==null){
			for(Unit u: Unit.values()) {
				_listBoxUnit.addItem(u.name(),u.name());
			}
		}else{
			for(Unit u:unit.getRelativeTypes()){
				_listBoxUnit.addItem(u.name(),u.name());
			}
		}*/

	}

	@Override
	public void addUnitChangedHandler(IUnitChangedHandler unitChangedHandler) {
		_unitChangedHandler=unitChangedHandler;
	}

}

package org.tagaprice.client.generics.widgets;

import org.tagaprice.shared.entities.Unit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is just a wrapper!
 *
 */
public class UnitSelecter extends Composite implements IUnitSelecter {

	private IUnitSelecter unitSelecter = GWT.create(IUnitSelecter.class);

	public UnitSelecter(){
		initWidget(unitSelecter.asWidget());
	}

	@Override
	public void setRelatedUnit(Unit unit) {
		unitSelecter.setRelatedUnit(unit);
	}

	@Override
	public void setUnit(Unit unit) {
		unitSelecter.setUnit(unit);
	}

	@Override
	public Unit getUnit() {
		return unitSelecter.getUnit();
	}


	@Override
	public void addUnitChangedHandler(IUnitChangedHandler unitChangedHandler){
		unitSelecter.addUnitChangedHandler(unitChangedHandler);
	}

	@Override
	public void setReadOnly(boolean read) {
		unitSelecter.setReadOnly(read);
	}

	@Override
	public boolean isReadOnly() {
		return unitSelecter.isReadOnly();
	}

	@Override
	public void config(boolean isHeadline) {
		unitSelecter.config(isHeadline);
	}

}

package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.IQuantitySelecter;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.user.client.ui.*;

/**
 * This class will manage the Quantity selection, and will also communicate with the server.
 *
 */
public class QuantitySelecter extends Composite implements IQuantitySelecter {

	private HorizontalPanel _vp = new HorizontalPanel();
	private TextBox _quantity = new TextBox();
	private ListBox _units = new ListBox();

	public QuantitySelecter() {
		initWidget(_vp);
		_vp.add(_quantity);
		_vp.add(_units);

		_quantity.setText("test");

		for(Unit u: Unit.values()) {
			_units.addItem(u.name());
		}
	}

	@Override
	public void setQuantity(IQuantity quantity) {
		_quantity.setText(""+quantity.getQuantity());
		int pos = 0;

		for(int i= 0; i < Unit.values().length; i++ ) {
			if(Unit.values()[i].equals(quantity.getUnit())) {
				pos=i;
			}
		}
		_units.setSelectedIndex(pos);
	}

	@Override
	public IQuantity getQuantity() {
		return new Quantity(new Double(_quantity.getText()), Unit.valueOf(_units.getItemText(_units.getSelectedIndex())));
	}

}

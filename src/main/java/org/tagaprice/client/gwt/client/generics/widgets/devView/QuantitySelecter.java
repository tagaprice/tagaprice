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
	private IQuantity _curQuantity;

	public QuantitySelecter() {
		initWidget(_vp);
		_vp.add(_quantity);
		_vp.add(_units);

		_quantity.setText("test");

		for(Unit u: Unit.values()) {
			_units.addItem(u.name());
		}


		//TODO set standard Quantity

	}

	@Override
	public void setQuantity(IQuantity quantity) {
		_curQuantity=quantity;
		_quantity.setText(""+quantity.getQuantity());
	}

	@Override
	public IQuantity getQuantity() {
		return _curQuantity;
	}

}

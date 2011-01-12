package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.IQuantitySelecter;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This class will manage the Quantity selection, and will also communicate with the server.
 *
 */
public class QuantitySelecter extends Composite implements IQuantitySelecter {

	private VerticalPanel _vp = new VerticalPanel();
	private TextBox _quantity = new TextBox();
	private ListBox _units = new ListBox();
	private IQuantity _curQuantity;

	public QuantitySelecter() {
		initWidget(_vp);
		_vp.add(_quantity);
		_vp.add(_units);

		_quantity.setText("test");


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

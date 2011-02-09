package org.tagaprice.client.gwt.client.generics.widgets.devView;

import org.tagaprice.client.gwt.client.generics.widgets.IQuantitySelecter;
import org.tagaprice.client.gwt.client.generics.widgets.UnitSelecter;
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
	private UnitSelecter _unitSelecter = new UnitSelecter();

	public QuantitySelecter() {
		initWidget(_vp);
		_vp.add(_quantity);
		_vp.add(_unitSelecter);

		_quantity.setText("test");


	}

	@Override
	public void setQuantity(IQuantity quantity) {
		_quantity.setText(""+quantity.getQuantity());
		_unitSelecter.setUnit(quantity.getUnit());
	}

	@Override
	public IQuantity getQuantity() {
		return new Quantity(new Double(_quantity.getText()), _unitSelecter.getUnit());
	}

	@Override
	public void setRelatedUnit(Unit unit){
		_unitSelecter.setRelatedUnit(unit);
	}



}

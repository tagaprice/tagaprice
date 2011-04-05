package org.tagaprice.client.generics.widgets.devView;

import org.tagaprice.client.generics.widgets.IQuantityChangeHandler;
import org.tagaprice.client.generics.widgets.IQuantitySelecter;
import org.tagaprice.client.generics.widgets.IUnitChangedHandler;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.*;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;

/**
 * This class will manage the Quantity selection, and will also communicate with the server.
 *
 */
public class QuantitySelecter extends Composite implements IQuantitySelecter {

	private HorizontalPanel _vp = new HorizontalPanel();
	private TextBox _quantity = new TextBox();
	private UnitSelecter _unitSelecter = new UnitSelecter();
	private IQuantityChangeHandler _quantityChangeHandler;

	public QuantitySelecter() {
		initWidget(_vp);
		_vp.add(_quantity);
		_vp.add(_unitSelecter);


		//add handler
		_quantity.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent arg0) {
				if(_quantityChangeHandler!=null)_quantityChangeHandler.onChange(getQuantity());
			}
		});

		_unitSelecter.addUnitChangedHandler(new IUnitChangedHandler() {

			@Override
			public void onChange(Unit unit) {
				if(_quantityChangeHandler!=null)_quantityChangeHandler.onChange(getQuantity());
			}
		});

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

	@Override
	public void addQuantityChangeHandler(IQuantityChangeHandler quantityChangeHandler) {
		_quantityChangeHandler=quantityChangeHandler;
	}




}

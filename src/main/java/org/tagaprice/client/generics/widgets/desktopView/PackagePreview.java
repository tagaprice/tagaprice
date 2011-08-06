package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackagePreview extends APreviewWidget {

	protected VerticalPanel _vePaPricePack = new VerticalPanel();
	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private MorphWidget _quantity = new MorphWidget();
	private UnitSelecter _unitSelecter = new UnitSelecter();
	private boolean _readonly = true;
	
	public PackagePreview(Product product, Package pack) {
		super(product.getTitle(), null);
		
		//Package
		if(pack!=null){
			_quantity.config(Type.DOUBLE, true, null, true, false);
			_quantity.setValue(pack.getQuantity().getQuantity().toPlainString());
			_quantity.setWidth("40px");
			_hoPa1.add(_quantity);
			
			//setUnit
			_unitSelecter.setUnit(pack.getQuantity().getUnit());
			_unitSelecter.setRelatedUnit(pack.getQuantity().getUnit());
			_hoPa1.add(_unitSelecter);
			
			_vePaPricePack.add(_hoPa1);
		}
		
		_mainHoPa.add(_vePaPricePack);
	}
	
	public boolean isReadOnly() {
		return _readonly;
	}
	
	public void setReadOnly(boolean read) {
		_readonly=read;
		_unitSelecter.setReadOnly(_readonly);
		_quantity.setReadOnly(_readonly);
		
	}
}

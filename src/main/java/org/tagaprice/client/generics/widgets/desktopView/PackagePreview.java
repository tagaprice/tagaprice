package org.tagaprice.client.generics.widgets.desktopView;

import java.math.BigDecimal;

import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.shared.entities.Quantity;
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
	private Package _pack;
	
	public PackagePreview(Product product, Package pack) {
		super(product.getTitle(), null);
		
		_pack=pack;
		
		//Package
		if(_pack!=null){
			_quantity.config(Type.DOUBLE, true, null, true, false);
			_quantity.setValue(_pack.getQuantity().getQuantity().toPlainString());
			_quantity.setWidth("40px");
			_hoPa1.add(_quantity);
			
			//setUnit
			_unitSelecter.setUnit(_pack.getQuantity().getUnit());
			_unitSelecter.setRelatedUnit(_pack.getQuantity().getUnit());
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
	
	public Package getPackage(){
		_pack.setQuantity(new Quantity(new BigDecimal(_quantity.getValue()),
				_unitSelecter.getUnit()));
		
		
		return _pack;
	}
}

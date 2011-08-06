package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackagePreview extends APreviewWidget {

	private VerticalPanel _vePaPricePack = new VerticalPanel();
	
	public PackagePreview(Product product, Package pack) {
		super(product.getTitle(), null);
		
		//Package
		if(pack!=null){
			Label _packageSize = new Label(pack.getQuantity().getQuantity()+""+pack.getQuantity().getUnit().getTitle());
			_vePaPricePack.add(_packageSize);
			_hoPa1.add(_vePaPricePack);
		}
	}
}

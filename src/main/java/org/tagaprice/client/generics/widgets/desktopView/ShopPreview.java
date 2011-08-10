package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.user.client.ui.IsWidget;

public class ShopPreview extends APreviewWidget {

	public ShopPreview(Shop shop) {
		super(shop.getTitle(), null);
		// TODO Auto-generated constructor stub
	}
	
	public void addHoverWidget(IsWidget widget){
		_mainHoverHoPa.add(widget);
	}

}

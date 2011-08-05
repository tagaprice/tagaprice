package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.shared.entities.productmanagement.Product;

import com.google.gwt.user.client.ui.Label;

public class ProductPreview extends APreviewWidget {

	public ProductPreview(Product product) {
		super(product.getTitle(),null);
		
		
	}
}

package org.tagaprice.client.gwt.client.features.productmanagement;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.NotificationMole;

/**
 * forwards calls to the real ProductService. makes caching possible.
 * 
 */

public class ProductServiceDispatchImpl implements ProductServiceDispatch {
	private static IProductServiceAsync productServiceAsync = GWT.create(IProductService.class);

	private NotificationMole mole;

	@Override
	public void setMole(NotificationMole mole) {
		this.mole = mole;
	}

	@Override
	public void getProduct(IRevisionId revisionId, AsyncCallback<IProduct> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveProduct(IProduct product, AsyncCallback<IProduct> callback) {
		// TODO Auto-generated method stub

	}

}

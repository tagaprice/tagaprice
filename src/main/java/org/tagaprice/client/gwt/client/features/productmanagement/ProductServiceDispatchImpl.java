package org.tagaprice.client.gwt.client.features.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.NotificationMole;

/**
 * forwards calls to the real ProductService. makes caching possible.
 * 
 */

public class ProductServiceDispatchImpl implements ProductServiceDispatch {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceDispatchImpl.class);
	private static IProductServiceAsync productServiceAsync = GWT.create(IProductService.class);

	private NotificationMole mole;

	@Override
	public void setMole(NotificationMole mole) {
		this.mole = mole;
	}

	@Override
	public void getProduct(IRevisionId revisionId, AsyncCallback<IProduct> callback) {
		logger.log("get Product");
		ProductServiceDispatchImpl.productServiceAsync.getProduct(revisionId, callback);
	}

	@Override
	public void saveProduct(IProduct product, AsyncCallback<IProduct> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getProducts(IProduct searchCriteria, AsyncCallback<ArrayList<IProduct>> callback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getCategories(AsyncCallback<ArrayList<ICategory>> callback) {
		// TODO Auto-generated method stub

	}

}

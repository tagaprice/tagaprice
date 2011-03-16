package org.tagaprice.client.gwt.shared.rpc.searchmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.BoundingBox;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ISearchServiceAsync {

	void searchShop(String searchString, BoundingBox bbox, AsyncCallback<ArrayList<IShop>> callback);

	void searchProduct(String searchString, IShop shop,  AsyncCallback<ArrayList<IProduct>> callback);
}

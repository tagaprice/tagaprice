package org.tagaprice.shared.rpc.searchmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ISearchServiceAsync {

	void searchShop(String searchString, BoundingBox bbox, AsyncCallback<ArrayList<IShop>> callback);

	void searchProduct(String searchString, ISubsidiary address,  AsyncCallback<ArrayList<IProduct>> callback);

	void searchAddress(double lat, double lng,  AsyncCallback<Address> callback);
}

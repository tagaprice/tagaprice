package org.tagaprice.shared.rpc.searchmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ISearchServiceAsync {

	void searchShop(String searchString, BoundingBox bbox, AsyncCallback<List<IShop>> callback);

	void searchProduct(String searchString, ISubsidiary address,  AsyncCallback<List<IProduct>> callback);

	void searchAddress(double lat, double lng,  AsyncCallback<Address> callback);
}

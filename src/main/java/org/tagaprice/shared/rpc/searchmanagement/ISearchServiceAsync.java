package org.tagaprice.shared.rpc.searchmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ISearchServiceAsync {

	void searchShop(String searchString, BoundingBox bbox, AsyncCallback<List<Shop>> callback);

	void searchProduct(String searchString, Shop address,  AsyncCallback<List<Product>> callback);

	void searchAddress(double lat, double lng,  AsyncCallback<Address> callback);
}

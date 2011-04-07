package org.tagaprice.shared.rpc.shopmanagement;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IShopServiceAsync {

	void getShop(String id, String revision, AsyncCallback<Shop> callback);
	void getShop(String id, AsyncCallback<Shop> callback);
	

	void getShops(Shop searchCriteria, AsyncCallback<List<Shop>> callback);

	void saveShop(Shop shop, AsyncCallback<Shop> callback);


}

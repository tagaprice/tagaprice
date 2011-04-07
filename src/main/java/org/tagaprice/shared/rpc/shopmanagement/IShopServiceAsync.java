package org.tagaprice.shared.rpc.shopmanagement;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.IShop;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IShopServiceAsync {

	void getShop(String id, String revision, AsyncCallback<IShop> callback);
	void getShop(String id, AsyncCallback<IShop> callback);
	

	void getShops(IShop searchCriteria, AsyncCallback<List<IShop>> callback);

	void saveShop(IShop shop, AsyncCallback<IShop> callback);


}

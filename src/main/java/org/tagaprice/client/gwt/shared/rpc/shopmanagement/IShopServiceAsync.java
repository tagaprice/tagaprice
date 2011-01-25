package org.tagaprice.client.gwt.shared.rpc.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IShopServiceAsync {

	void getShop(IRevisionId revisionId, AsyncCallback<IShop> callback);

	void getShops(IShop searchCriteria, AsyncCallback<ArrayList<IShop>> callback);

	void save(IShop shop, AsyncCallback<IShop> callback);

}

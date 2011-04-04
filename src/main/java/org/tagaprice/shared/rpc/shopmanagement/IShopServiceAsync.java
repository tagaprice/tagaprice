package org.tagaprice.shared.rpc.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IShopServiceAsync {

	void getShop(IRevisionId revisionId, AsyncCallback<IShop> callback);

	void getShops(IShop searchCriteria, AsyncCallback<ArrayList<IShop>> callback);

	void saveShop(IShop shop, AsyncCallback<IShop> callback);

	void getAddress(IRevisionId revisionId, AsyncCallback<ISubsidiary> callback);

	void saveAddress(ISubsidiary address, AsyncCallback<ISubsidiary> callback);

}

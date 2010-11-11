package org.tagaprice.shared.rpc;

import org.tagaprice.shared.data.ShopData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShopHandlerAsync {
	void get(long id, AsyncCallback<ShopData> callback)
	throws IllegalArgumentException;

void save(ShopData data, AsyncCallback<ShopData> callback) 
	throws IllegalArgumentException;
}

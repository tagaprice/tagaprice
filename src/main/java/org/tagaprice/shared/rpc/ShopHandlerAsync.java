package org.tagaprice.shared.rpc;

import org.tagaprice.shared.data.Shop;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ShopHandlerAsync {
	void get(long id, AsyncCallback<Shop> callback)
	throws IllegalArgumentException;

void save(Shop data, AsyncCallback<Shop> callback) 
	throws IllegalArgumentException;
}

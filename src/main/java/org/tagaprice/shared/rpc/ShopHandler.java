package org.tagaprice.shared.rpc;

import org.tagaprice.shared.ShopData;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/shop")
public interface ShopHandler {
	ShopData get(Long id) throws IllegalArgumentException;
	ShopData save(ShopData data)  throws IllegalArgumentException;

}

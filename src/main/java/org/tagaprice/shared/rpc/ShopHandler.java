package org.tagaprice.shared.rpc;

import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/shop")
public interface ShopHandler extends RemoteService {
	Shop get(long id) throws IllegalArgumentException;
	Shop save(Shop data)  throws IllegalArgumentException, InvalidLoginException;

}

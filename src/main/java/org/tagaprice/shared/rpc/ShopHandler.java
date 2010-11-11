package org.tagaprice.shared.rpc;

import org.tagaprice.shared.data.ShopData;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/shop")
public interface ShopHandler extends RemoteService {
	ShopData get(long id) throws IllegalArgumentException;
	ShopData save(ShopData data)  throws IllegalArgumentException, InvalidLoginException;

}

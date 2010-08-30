package org.tagaprice.shared.rpc;

import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/shop")
public interface ShopHandler extends RemoteService {
	ShopData get(Long id) throws IllegalArgumentException;
	ShopData save(ShopData data)  throws IllegalArgumentException, InvalidLoginException;

}

package org.tagaprice.shared.rpc;

import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.exception.DAOException;
import org.tagaprice.shared.exception.InvalidLoginException;
import org.tagaprice.shared.exception.ServerException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/shop")
public interface ShopHandler extends RemoteService {
	ShopData get(long id) throws IllegalArgumentException, ServerException;
	ShopData save(ShopData data)  throws IllegalArgumentException, InvalidLoginException, DAOException, ServerException;

}

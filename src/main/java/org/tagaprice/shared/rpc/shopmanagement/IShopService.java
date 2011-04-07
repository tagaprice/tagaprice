package org.tagaprice.shared.rpc.shopmanagement;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shopservice")
public interface IShopService extends RemoteService {

	public List<IShop> getShops(IShop searchCriteria);

	public IShop getShop(String id, String revision);
	public IShop getShop(String id);

	public IShop saveShop(IShop shop) throws UserNotLoggedInException;


}

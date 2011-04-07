package org.tagaprice.shared.rpc.shopmanagement;

import java.util.List;

import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shopservice")
public interface IShopService extends RemoteService {

	public List<Shop> getShops(Shop searchCriteria);

	public Shop getShop(String id, String revision);
	public Shop getShop(String id);

	public Shop saveShop(Shop shop) throws UserNotLoggedInException;


}

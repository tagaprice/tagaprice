package org.tagaprice.client.gwt.shared.rpc.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.core.api.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shopservice")
public interface IShopService extends RemoteService {

	public ArrayList<IShop> getShops(IShop searchCriteria);

	public IShop getShop(IRevisionId revisionId);

	public IShop save(IShop shop) throws UserNotLoggedInException;

}

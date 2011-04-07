package org.tagaprice.shared.rpc.shopmanagement;

import java.util.List;

import org.tagaprice.shared.entities.*;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("shopservice")
public interface IShopService extends RemoteService {

	public List<IShop> getShops(IShop searchCriteria);

	public IShop getShop(IRevisionId revisionId);

	public IShop saveShop(IShop shop) throws UserNotLoggedInException;

	public ISubsidiary getAddress(IRevisionId revisionId);

	public ISubsidiary saveAddress(ISubsidiary address) throws UserNotLoggedInException;

}

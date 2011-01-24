package org.tagaprice.client.gwt.shared.rpc.shopmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("productservice")
public interface IShopService extends RemoteService {

	public ArrayList<IShop> getShops(IShop searchCriteria);

	public IShop getShop(IRevisionId revisionId);

	public IShop save(IShop shop);

}

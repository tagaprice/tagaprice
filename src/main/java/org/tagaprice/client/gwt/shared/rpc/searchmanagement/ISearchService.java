package org.tagaprice.client.gwt.shared.rpc.searchmanagement;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import org.tagaprice.client.gwt.shared.entities.BoundingBox;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

@RemoteServiceRelativePath("searchservice")
public interface ISearchService extends RemoteService {

	/**
	 * Returns a list of shops specified by a searchString and a BoundingBox
	 * @param searchString Name of the Shop
	 * @param bbox the area where to search for this shop
	 * @return a list of shop results. (Is never null)
	 */
	ArrayList<IShop> searchShop(String searchString, BoundingBox bbox);


}

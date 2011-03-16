package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.BoundingBox;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.rpc.searchmanagement.ISearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7050499202457079509L;

	@Override
	public ArrayList<IShop> searchShop(String searchString, BoundingBox bbox) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IProduct> searchProduct(String searchString, IShop shop) {
		// TODO Auto-generated method stub
		return null;
	}

}

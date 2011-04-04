package org.tagaprice.server.mock;

import java.util.ArrayList;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7050499202457079509L;

	private ShopServiceImpl _mockShopService = new ShopServiceImpl();
	private ProductServiceImpl _mockPrductService = new ProductServiceImpl();

	@Override
	public ArrayList<IShop> searchShop(String searchString, BoundingBox bbox) {

		//Returns all shops
		return _mockShopService.getShops(null);
	}

	@Override
	public ArrayList<IProduct> searchProduct(String searchString, ISubsidiary shop) {

		//Returns all shops
		IProduct _dumpProduc = new Product();
		_dumpProduc.setTitle(searchString);
		return _mockPrductService.getProducts(_dumpProduc);
	}

}

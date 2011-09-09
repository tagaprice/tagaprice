package org.tagaprice.shared.rpc.searchmanagement;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

@RemoteServiceRelativePath("searchservice")
public interface ISearchService extends RemoteService {

	/**
	 * Returns a list of shops specified by a searchString and a BoundingBox
	 * 
	 * @param searchString
	 *            Name of the Shop
	 * @param bbox
	 *            the area where to search for this shop
	 * @return a list of shop results. (Is never null)
	 * @throws DaoException if something went wrong while requesting the data
	 */
	List<Shop> searchShop(String searchString, BoundingBox bbox) throws DaoException;

	/**
	 * Returns a list of {@link Product} sorted by {@link Shop} plus {@link IAddress}, than {@link Shop}, than
	 * similar
	 * products.
	 * 
	 * @param searchString
	 *            Name of the Product
	 * @param address
	 *            name of the {@link IAddress} where the search process should begin
	 * @return a list of {@link Product} sorted by {@link IAddress} plus {@link IAddress}, than {@link IAddress}, than
	 *         similar
	 *         products.
	 * @throws DaoException if something went wrong while requesting the data
	 */
	List<Product> searchProduct(String searchString, Shop address) throws DaoException;


	List<StatisticResult> searchProductPrices(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException;

	List<StatisticResult> searchShopPrices(String shopId, Date begin, Date end) throws DaoException;

	List<Document> search(String query, BoundingBox bbox) throws DaoException;

}

package org.tagaprice.server.rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {
	private static final long serialVersionUID = 1L;

	private IShopDao shopDAO;
	private IProductDao productDAO;
	private ISearchDao searchDAO;
	private IStatisticDao statisticDao;
	

	public SearchServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		shopDAO = daoFactory.getShopDao();
		productDAO = daoFactory.getProductDao();
		searchDAO = daoFactory.getSearchDao();
		statisticDao = daoFactory.getStatisticDao();
	}

	@Override
	public List<Document> search(String query, BoundingBox bbox) throws DaoException {
		return searchDAO.search("*"+query.trim()+"*",bbox);
	}


	@Override
	public List<Shop> searchShop(String searchString, BoundingBox bbox) throws DaoException {
		if(!searchString.trim().isEmpty())
			return shopDAO.find("*"+searchString.trim()+"*");
		
		return new ArrayList<Shop>();
	}

	@Override
	public List<Product> searchProduct(String searchString, Shop shop) throws DaoException {
		if(!searchString.trim().isEmpty())
			return productDAO.find("*"+searchString.trim()+"*");
		return new ArrayList<Product>();
	}





	@Override
	public ArrayList<StatisticResult> searchProductPrices(String productId, BoundingBox bbox, Date begin, Date end) {
		return statisticDao.searchPricesViaProduct(productId, bbox, begin, end);
	}

	@Override
	public List<StatisticResult> searchShopPrices(String shopId, BoundingBox bbox, Date begin, Date end) {
		return statisticDao.searchPricesViaShop(shopId, bbox, begin, end);
	}

}

class GeoNamesJson {

	private Segmente[] streetSegment;

	public GeoNamesJson() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the streetSegment
	 */
	public Segmente[] getStreetSegment() {
		return streetSegment;
	}



}

class Segmente{
	private String name;

	public Segmente() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


}

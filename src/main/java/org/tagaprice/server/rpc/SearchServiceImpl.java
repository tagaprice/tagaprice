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
		return searchDAO.search("*"+query.trim()+"*",bbox, 10);
	}


	@Override
	public List<Shop> searchShop(String searchString, BoundingBox bbox) throws DaoException {		
		ArrayList<Shop> list = new ArrayList<Shop>();
		
		if(!searchString.trim().isEmpty()){
			for(Document d:searchDAO.searchShop("*"+searchString.trim()+"*", bbox, 10)){
				if(d.getDocType().equals("shop"))
					list.add(Shop.fromDocument(d));
			}
			
		}
		
		return list;
	}

	@Override
	public List<Product> searchProduct(String searchString, Shop shop) throws DaoException {
		
		ArrayList<Product> list = new ArrayList<Product>();
		
		if(!searchString.trim().isEmpty()){
			for(Document d:searchDAO.searchProduct("*"+searchString.trim()+"*", 10)){
				if(d.getDocType().equals("product"))
					list.add(Product.fromDocument(d));
			}
			
		}
		
		return list;
	}

	



	@Override
	public ArrayList<StatisticResult> searchProductPrices(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException {
		return statisticDao.searchPricesViaProduct(productId, bbox, begin, end);
	}

	@Override
	public List<StatisticResult> searchShopPrices(String shopId, BoundingBox bbox, Date begin, Date end) {
		return statisticDao.searchPricesViaShop(shopId, bbox, begin, end);
	}

}

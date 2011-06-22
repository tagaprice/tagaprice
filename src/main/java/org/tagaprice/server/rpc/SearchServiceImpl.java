package org.tagaprice.server.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {
	private static final long serialVersionUID = 1L;

	private IShopDao shopDAO;
	private IProductDao productDAO;
	private IStatisticDao statisticDao;

	public SearchServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		shopDAO = daoFactory.getShopDao();
		productDAO = daoFactory.getProductDao();
		statisticDao = daoFactory.getStatisticDao();
	}




	@Override
	public List<Shop> searchShop(String searchString, BoundingBox bbox) throws DaoException {
		return shopDAO.find(searchString);
	}

	@Override
	public List<Product> searchProduct(String searchString, Shop shop) throws DaoException {

		//Returns all shops
		Product _dumpProduct = new Product();
		_dumpProduct.setTitle(searchString);
		return productDAO.find(_dumpProduct);
	}

	@Override
	public Address searchAddress(double lat, double lng) {
		try {
			Log.debug("findService: "+lat+":"+lng);
			URL urlg = new URL("http://api.geonames.org/findNearbyStreetsOSMJSON?lat="+lat+"&lng="+lng+"&username=tagaprice");
			InputStream isg = urlg.openStream();
			//Geonames
			Gson gsonG = new Gson();
			GeoNamesJson g = gsonG.fromJson(new InputStreamReader(isg), GeoNamesJson.class);

			if(g.getStreetSegment()!=null & g.getStreetSegment().length>0){
				Log.debug("findService: found: "+g.getStreetSegment()[0].getName());
				return new Address(g.getStreetSegment()[0].getName(), lat, lng);
			}else{
				Log.debug("Not Found");
				return null;
			}



		} catch (MalformedURLException e) {
			Log.warn(e.toString());
		} catch (IOException e) {
			Log.error(e.toString());
		}

		return null;
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


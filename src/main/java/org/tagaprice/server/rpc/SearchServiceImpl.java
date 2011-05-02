package org.tagaprice.server.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {
	private static final long serialVersionUID = 1L;

	private IShopDao shopDAO;
	private IProductDao productDAO;
	private MyLogger _logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	public SearchServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		shopDAO = daoFactory.getShopDAO();
		productDAO = daoFactory.getProductDAO();
	}




	@Override
	public List<Shop> searchShop(String searchString, BoundingBox bbox) throws DaoException {
		return shopDAO.list();
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
			_logger.log("findService: "+lat+":"+lng);
			URL urlg = new URL("http://api.geonames.org/findNearbyStreetsOSMJSON?lat="+lat+"&lng="+lng+"&username=tagaprice");
			InputStream isg = urlg.openStream();
			//Geonames
			Gson gsonG = new Gson();
			GeoNamesJson g = gsonG.fromJson(new InputStreamReader(isg), GeoNamesJson.class);

			if(g.getStreetSegment()!=null & g.getStreetSegment().length>0){
				_logger.log("findService: found: "+g.getStreetSegment()[0].getName());
				return new Address(g.getStreetSegment()[0].getName(), lat, lng);
			}else{
				_logger.log("Not Found");
				return null;
			}



		} catch (MalformedURLException e) {
			_logger.log(e.toString());
		} catch (IOException e) {
			_logger.log(e.toString());
		}

		return null;
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


package org.tagaprice.server.dao.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import com.google.gson.Gson;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements ISearchService {
	private static final long serialVersionUID = 1L;

	private ShopServiceImpl _mockShopService = new ShopServiceImpl();
	private ProductServiceImpl _mockPrductService = new ProductServiceImpl();
	private MyLogger _logger = LoggerFactory.getLogger(SearchServiceImpl.class);

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


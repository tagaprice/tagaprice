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
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
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
	private IReceiptDao receiptDAO;
	private MyLogger _logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	public SearchServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		shopDAO = daoFactory.getShopDao();
		productDAO = daoFactory.getProductDao();
		receiptDAO = daoFactory.getReceiptDao();
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




	@Override
	public ArrayList<StatisticResult> searchProductPrices(String id, BoundingBox bbox, Date begin, Date end) {
		//TODO search
		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:receiptDAO.list()){
				if(r.getShop().getAddress().getLat()<bbox.getNorthEastLat() &&
						r.getShop().getAddress().getLat()>bbox.getSouthWestLat() &&
						r.getShop().getAddress().getLng()<bbox.getNorthEastLng() &&
						r.getShop().getAddress().getLng()>bbox.getSouthWestLng()){
					for(ReceiptEntry re:r.getReceiptEntries()){
						if(id.equals(re.getPackage().getProduct().getId())){
							rc.add(new StatisticResult(
									r.getDate(),
									r.getShop(),
									null,
									re.getPackage().getQuantity(),
									re.getPrice()));
						}

					}
				}

			}
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		/*
		{
			Shop s1 = new Shop(null, "Billa - Holzhausergasse 9");
			s1.setAddress(new Address("Holzhausergasse 9", 48.21977, 16.38901));
			test.add(new StatisticResult(
					new Date(),
					s1,
					null,
					new Quantity(new BigDecimal("200"),new Unit(null, "ml")),
					new Price(new BigDecimal("15"), Currency.euro)));
		}
		 */
		return rc;
	}




	@Override
	public List<StatisticResult> searchShopPrices(String id, BoundingBox bbox, Date begin, Date end) {
		// TODO Auto-generated method stub
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


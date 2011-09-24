package org.tagaprice.server.dao.couchdb.statisticAggregator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.server.dao.couchdb.CouchDbConfig;
import org.tagaprice.server.dao.couchdb.CouchDbDaoFactory;
import org.tagaprice.server.dao.couchdb.StatisticDao;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Quantity;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.Address.LatLon;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Currency;
import org.tagaprice.shared.entities.receiptManagement.Price;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class StatisticAggregator extends Thread {
	private HttpClient _client;
	private HttpGet _getRequest;
	private boolean _stopFlag = false;
	private StatisticDao _statisticDao = null;
	private IPackageDao _packageDao = null;
	private IProductDao _productDao = null;
	private ICategoryDao _productCategoryDao = null;
	private ICategoryDao _shopCategoryDao = null;
	private IShopDao _shopDao = null;
	private IUnitDao _unitDao = null;
	
	public StatisticAggregator(CouchDbDaoFactory daoFactory, StatisticDao statisticDao) {
		_statisticDao = statisticDao;
		_packageDao = daoFactory.getPackageDao();
		_productDao = daoFactory.getProductDao();
		_productCategoryDao = daoFactory.getProductCategoryDao();
		_shopCategoryDao = daoFactory.getShopCategoryDao();
		_shopDao = daoFactory.getShopDao();
		_unitDao = daoFactory.getUnitDao();
	}

	public void run() {
		_stopFlag = false;
		long seqNr = 0;

		while (!_stopFlag) {
			try {
				seqNr = Math.max(seqNr, _statisticDao.getMaxSequenceNr());
				CouchDbConfig configuration = CouchDbDaoFactory.getConfiguration();
				
				String host = configuration.getCouchHost();
				String userInfo = null;
				if (configuration.getCouchUser() != null || configuration.getCouchPassword() != null) {
					userInfo = configuration.getCouchUser()+":"+configuration.getCouchPassword();
				}
				int port = configuration.getCouchPort();
				String path = "/"+configuration.getCouchDatabase()+"/_changes";
				String query = "feed=continuous&include_docs=true&since="+seqNr;
				
				_client = new DefaultHttpClient();
				URI uri = new URI("http", userInfo, host, port, path, query, null);
				_getRequest = new HttpGet(uri);
				HttpResponse response = _client.execute(_getRequest);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while ((line = reader.readLine()) != null) {
						Log.debug("StatisticAggregator got changes line: '"+line+"'");
						if (line.length() > 0) {
							seqNr = Math.max(seqNr, parseLine(line));
						}
					}
				}
			} catch (Throwable e) {
				Log.error("Exception in StatisticAggregator caught: "+e.getMessage(), e);
			}
			
			// make sure we won't hang in a CPU eating loop if there is an error 
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				//ignore it
			}
		}
	}
	
	private long parseLine(String line) throws DaoException {
		CouchChange changes = JSONParser.defaultJSONParser().parse(CouchChange.class, line);
		Document document = changes.getDoc();
		if (changes.getDeleted() == true) {
			_statisticDao.delete(_statisticDao.getAffectedIDs(changes.getId()));
		}
		else if (document != null && document.getDocTypeEnum() != null) {
			switch (document.getDocTypeEnum()) {
			case RECEIPT:
				receiptChanged(changes);
				break;
			case PACKAGE:
			case PRODUCT:
			case SHOP:
				documentChanged(changes);
				break;
			}
		}
		return changes.getSeq();
	}
	
	public synchronized void stopListening() {
		_stopFlag = true;
		_getRequest.abort();
	}
	
	private static Category _updateCategory(Document document, String oldCategoryId, ICategoryDao categoryDao) throws DaoException {
		Category rc = null;
		if (document.getPropertyList().containsKey("categoryId")) {
			String categoryId = document.getProperty("categoryId").toString();
			if (!categoryId.equals(oldCategoryId)) {
				Category category = categoryDao.get(categoryId);
				
				if (category != null) rc = new Category(null, categoryId, null, category.getTitle(), null);
			}
		}

		return rc;
	}
	
	private void documentChanged(CouchChange changes) throws DaoException {
		List<StatisticResult> affectedStatistics = _statisticDao.getAffected(changes.getId());
		Document document = changes.getDoc();

		for (StatisticResult statistic: affectedStatistics) {
			switch (document.getDocTypeEnum()) {
			case PRODUCT: {
				Product targetProduct = statistic.getProduct();
				// update title
				targetProduct.setTitle(document.getTitle());
				
				// update category
				targetProduct.setCategory(_updateCategory(document, targetProduct.getCategoryId(), _productCategoryDao));
			}	break;
			case PACKAGE:
				// update quantity
				if (document.getPropertyList().containsKey("quantity")) {
					Object quantityObject = document.getProperty("quantity");
					if (quantityObject instanceof Map<?,?>) {
						Map<?,?> quantityMap = (Map<?,?>) quantityObject;
						Unit unit = _unitDao.get(quantityMap.get("unitId").toString());
						Quantity quantity = new Quantity(new BigDecimal(
								quantityMap.get("quantity").toString()),
								new Unit(null, unit.getId(), null, unit.getTitle(), null, 0));
						statistic.getPackage().setQuantity(quantity);
					}
					
				}
				break;
			case SHOP: {
				Shop targetShop = statistic.getShop();
				
				// update title
				targetShop.setTitle(document.getTitle());
				
				// update category
				targetShop.setCategory(_updateCategory(document, targetShop.getCategoryId(), _shopCategoryDao));
				
				// update address (lat+lon)
				if (document.getAddress() != null) {
					statistic.getAddress().setPos(document.getAddress().getPos());
				}
				else if (statistic.getAddress() != null) statistic.setAddress(null);
			}	break;
			case PRODUCTCATEGORY:
				// update title
				statistic.getProduct().getCategory().setTitle(document.getTitle());
				break;
			case SHOPCATEGORY:
				// update title
				statistic.getShop().getCategory().setTitle(document.getTitle());
				break;
			case UNIT:
				// update title
				statistic.getPackage().getQuantity().getUnit().setTitle(document.getTitle());
				// TODO: update parent
				break;
			}
			statistic.setSequenceNr(changes.getSeq());
			_statisticDao.update(statistic);
		}
	}
	
	private void receiptChanged(CouchChange changes) throws DaoException {
		Document document = changes.getDoc();
	
		// first delete all the old statistics documents that are associated to this Receipt
		_statisticDao.delete(_statisticDao.getIDsByReceipt(document.getId()));

		// then create a new one for each receipt entry
		Shop shop = null;
		Date timestamp = null;
		Category shopCategory = null;
		
		if (document.hasProperty("shopId")) {
			String shopId = document.getProperty("shopId").toString();
			if (shopId != null) {
				Shop originalShop = _shopDao.get(document.getProperty("shopId").toString());
				shop = new Shop(null, originalShop.getId(), null, originalShop.getTitle(), null);
				if (originalShop.getAddress() != null && originalShop.getAddress().getPos() != null) {
					LatLon pos = originalShop.getAddress().getPos();
					shop.setAddress(new Address(null, null, null, null, pos.getLat(), pos.getLon()));
				}
				if (originalShop.getCategoryId() != null) {
					shopCategory = new Category(null, originalShop.getCategoryId(), null, originalShop.getCategory().getTitle(), null);
				}
			}
		}

		if (document.hasProperty("timeStamp")) {
			String timestampString = document.getProperty("timeStamp").toString();
			if (timestampString.endsWith(".0")) {
				timestampString = timestampString.replace(".0", "");
			}
			if (!timestampString.contains("e") && !timestampString.contains("E")) {
				timestamp = new Date(new Long(timestampString));
			}
		}

		if (document.hasProperty("receiptEntries")) {
			Object entriesObject = document.getProperty("receiptEntries");
			if (entriesObject instanceof List<?>) {
				List<?> entries = (List<?>) entriesObject;
				for (Object entryObject: entries) {
					if (entryObject instanceof Map<?, ?>) {
						Map<?,?> entryMap = (Map<?,?>) entryObject;
						StatisticResult statistic = new StatisticResult();

						statistic.setShop(shop);
						statistic.setShopCategory(shopCategory);
						statistic.setDate(timestamp);
						statistic.setReceiptId(document.getId());
						statistic.setSequenceNr(changes.getSeq());
						

						// package and product
						if (entryMap.containsKey("packageId")) {
							String packageId = entryMap.get("packageId").toString();
							Package pkg = _packageDao.get(packageId);
							Product product = _productDao.get(pkg.getProductId());
							statistic.setPackage(new Package(null, pkg.getId(), null, new Quantity(pkg.getQuantity().getQuantity(), null)));
							if (product != null) {
								Category category = null;
								if (product.getCategory() != null) {
									category = new Category(null, product.getCategoryId(), null, product.getCategory().getTitle(), null);
								}
								statistic.setProduct(new Product(null, product.getId(), null, product.getTitle(), category, null));
							}
							String unitId = pkg.getQuantity().getUnitId();
							if (unitId != null) {
								Unit unit = _unitDao.get(unitId);
								statistic.setUnit(new Unit(null, unit.getId(), null, unit.getTitle(), null, unit.getFactor()));
								if (unit.getParentId() != null) {
									unit = _unitDao.get(unit.getParentId());
									statistic.setParentUnit(new Unit(null, unit.getId(), null, unit.getTitle(), null, 0));
								}
							}
						}

						// price
						if (entryMap.containsKey("price")) {
							Object priceObject = entryMap.get("price");
							if (priceObject instanceof Map<?,?>) {
								Map<?,?> priceMap = (Map<?,?>) priceObject;
								statistic.setPrice(new Price(new BigDecimal(priceMap.get("price").toString()), Currency.valueOf(priceMap.get("currency").toString())));
							}
						}
						String json = JSON.formatJSON(JSON.defaultJSON().forValue(statistic));
						System.err.println("JSON:\n"+json);
						_statisticDao.create(statistic);
					}
				}
			}
		}
	}
}

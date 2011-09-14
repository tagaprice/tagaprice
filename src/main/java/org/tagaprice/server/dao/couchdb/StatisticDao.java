package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.tagaprice.server.dao.IDaoClass;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {
	private NewElasticSearchClient m_searchClient;
	private IPackageDao m_packageDao;
	private IProductDao m_productDao;
	private IReceiptDao m_receiptDao;
	private IShopDao m_shopDao;

	public StatisticDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, StatisticResult.class, null, null);
		m_packageDao = daoFactory.getPackageDao();
		m_productDao = daoFactory.getProductDao();
		m_receiptDao = daoFactory.getReceiptDao();
		m_shopDao = daoFactory.getShopDao();
		m_searchClient = daoFactory.getElasticSearchClient();
	}

	@Override
	public List<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException {
		List<StatisticResult> rc = new ArrayList<StatisticResult>();
		
		// get all the packages of the product in question
		List<String> packageIDs = m_packageDao.listIDsByProduct(productId);
		// get all the shops in the specified bbox
		List<String> shopIDs = m_shopDao.findIDsInBBox(bbox);

		if (shopIDs.size() > 0) {
			// find all receipts that contain at least one of the packages AND at least one of the shops
			QueryBuilder queryBuilder = filteredQuery(
				matchAllQuery(),
				andFilter(
					termsFilter("packageId", packageIDs),
					termsFilter("shopId", shopIDs)
				)
			);
	
			SearchResponse searchResponse = m_searchClient.find(Document.Type.RECEIPT, queryBuilder);
			
			// cache the results to avoid fetching too much data
			Map<String, Package> packageCache = new HashMap<String, Package>();
			Map<String, Product> productCache = new HashMap<String, Product>();
			Map<String, Shop> shopCache = new HashMap<String, Shop>();
			
			for (SearchHit hit: searchResponse.getHits().getHits()) {
				Receipt receipt = m_receiptDao.getOnly(hit.getId());
				Shop shop = _getCached(m_shopDao, receipt.getShopId(), shopCache);
				for (ReceiptEntry entry: receipt.getReceiptEntries()) {
					if (packageIDs.contains(entry.getPackageId())) {
						Package pkg = _getCached(m_packageDao, entry.getPackageId(), packageCache);
						rc.add(new StatisticResult(receipt.getDate(), shop, _getCached(m_productDao, pkg.getProductId(), productCache), pkg.getQuantity(), entry.getPrice()));
					}
				}
			}
			
			// make sure those don't stay in the server's memory for too long
			packageCache.clear();
			productCache.clear();
			shopCache.clear();
		}
		
		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaShop(String shopId, Date begin, Date end) throws DaoException {
		List<StatisticResult> rc = new ArrayList<StatisticResult>();

		// get all packages that have been bought in this shop
		List<Receipt> receipts = m_receiptDao.listByShop(shopId, begin, end);
		
		for (Receipt receipt: receipts) {
			for (ReceiptEntry entry: receipt.getReceiptEntries()) {
				Package pkg = entry.getPackage();
				rc.add(new StatisticResult(receipt.getDate(), receipt.getShop(), pkg.getProduct(), pkg.getQuantity(), entry.getPrice()));
			}
		}
		
		return rc;
	}

	private static <T extends Document> T _getCached(IDaoClass<T> daoClass, String id, Map<String, T> cache) throws DaoException {
		if (!cache.containsKey(id)) {
			cache.put(id, daoClass.getOnly(id));
		}
		return cache.get(id);
	}

	@Override
	public List<StatisticResult> searchPricesViaShopCategory(String categoryId,
			BoundingBox bbox, Date begin, Date end) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatisticResult> searchPricesViaProductCategory(
			String categoryId, BoundingBox bbox, Date begin, Date end)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}

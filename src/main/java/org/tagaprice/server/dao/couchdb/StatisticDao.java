package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.server.dao.couchdb.elasticsearch.QueryObject;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.AndFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.TermsFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.FilteredQuery;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.MatchAllQuery;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Terms;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.Hit;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {
	private ElasticSearchClient m_searchClient;
	private IPackageDao m_packageDao;
	private IReceiptDao m_receiptDao;
	private IShopDao m_shopDao;

	public StatisticDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, StatisticResult.class, "statistic", daoFactory._getDocumentDao());
		m_packageDao = daoFactory.getPackageDao();
		m_receiptDao = daoFactory.getReceiptDao();
		m_shopDao = daoFactory.getShopDao();
		m_searchClient = daoFactory.getElasticSearchClient();
	}

	@Override
	public ArrayList<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException {
		// get all the packages of the product in question
		List<String> packageIDs = m_packageDao.listIDsByProduct(productId);
		// get all the shops in the specified bbox
		List<String> shopIDs = m_shopDao.findIDsInBBox(bbox);
		
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		// find all receipts that contain at least one of the packages AND at least one of the shops
		QueryObject queryObject = new QueryObject().query(
			new FilteredQuery().query(
				new MatchAllQuery()
			).filter(
				new AndFilter().addCondition(
					new TermsFilter().terms(new Terms("packageId", packageIDs))
				).addCondition(
					new TermsFilter().terms(new Terms("shopId", shopIDs))
				)
			)
		);

		SearchResult searchResult = m_searchClient.find(queryObject);
		
		for (Hit hit: searchResult.getHits().getHits()) {
			Receipt receipt = m_receiptDao.get(hit.getId());
			Shop shop = receipt.getShop();
			for (ReceiptEntry entry: receipt.getReceiptEntries()) {
				if (packageIDs.contains(entry.getPackageId())) {
					Package pkg = entry.getPackage();
					rc.add(new StatisticResult(receipt.getDate(), shop, pkg.getProduct(), pkg.getQuantity(), entry.getPrice()));
				}
			}
		}
		
		Log.debug("got "+rc.size()+" results");
		
		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaShop(String shopId, BoundingBox bbox, Date begin, Date end) {
		//TODO Search efficient via db or elasticsearch
		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:m_receiptDao.list()){
				if(bbox.contains(r.getAddress().getPos())) {

					if(shopId.equals(r.getShopId())){
						for(ReceiptEntry re:r.getReceiptEntries()){
							rc.add(new StatisticResult(
									r.getDate(),
									null,
									re.getPackage().getProduct(),
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

		return rc;
	}




}

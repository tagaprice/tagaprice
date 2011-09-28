package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Document.Type;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;


public class ShopDao extends DaoClass<Shop> implements IShopDao {
	private ElasticSearchClient m_searchClient;
	private ICategoryDao m_shopCategoryDAO;
	
	public ShopDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Shop.class, Document.Type.SHOP, null);
		m_searchClient = daoFactory.getElasticSearchClient();
		m_shopCategoryDAO = daoFactory.getShopCategoryDao();
	}

	@Override
	public List<Shop> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("shop/all", Shop.class, null, null);
		List<Shop> rc = new ArrayList<Shop>();
		
		for (ValueRow<?> row: result.getRows()) {
			Shop shop = get(row.getId());
			rc.add(shop);
		}
		
		return rc;
	}

	@Override
	public List<Shop> listByCategory(String shopCategoryId, BoundingBox bbox) {
		List<Shop> rc = new ArrayList<Shop>();

		QueryBuilder queryBuilder = constantScoreQuery(
			andFilter(
				ElasticSearchClient.createBoundingBoxFilter("address.pos", bbox),
				termFilter("categoryId", shopCategoryId)
			)
		);

		SearchResponse response = m_searchClient.find(queryBuilder, 0, 10, Type.SHOP);

		for (SearchHit hit: response.getHits().getHits()) {
			String json = org.svenson.JSON.defaultJSON().forValue(hit.getSource());
			rc.add(JSONParser.defaultJSONParser().parse(Shop.class, json));
		}

		return rc;
	}

	@Override
	protected void _injectFields(Shop ... shops) throws DaoException {
		Set<String> categoryIDs = new TreeSet<String>();
		
		for (Shop shop: shops) {
			if (shop.getCategoryId() != null) categoryIDs.add(shop.getCategoryId());
		}
		
		Map<String, Category> categories = m_shopCategoryDAO.getBulk(categoryIDs.toArray(new String[categoryIDs.size()]));
		
		for (Shop shop: shops) {
			if (shop.getCategoryId() != null) shop.setCategory(categories.get(shop.getCategoryId()));
		}
	}

}

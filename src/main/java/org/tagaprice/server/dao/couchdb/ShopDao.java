package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class ShopDao extends DaoClass<Shop> implements IShopDao {
	
	private ICategoryDao m_shopCategoryDAO;
	private NewElasticSearchClient m_searchClient;
	
	public ShopDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Shop.class, Document.Type.SHOP, null);
		m_shopCategoryDAO = daoFactory.getShopCategoryDao();
		m_searchClient = daoFactory.getElasticSearchClient();
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
	public List<String> findIDsInBBox(BoundingBox bbox) throws DaoException {
		List<String> rc = new ArrayList<String>();

		QueryBuilder queryBuilder = filteredQuery(
				matchAllQuery(),
				geoBoundingBoxFilter("address.pos")
					.bottomRight(bbox.getSouthLat(), bbox.getEastLon())
					.topLeft(bbox.getNorthLat(), bbox.getWestLon())
			);

		SearchResponse response = m_searchClient.find(Document.Type.SHOP, queryBuilder, 0, 100);

		for (SearchHit hit: response.getHits().getHits()) {
			rc.add(hit.getId());
		}
		
		return rc;
	}

	@Override
	protected void _injectFields(Shop shop) throws DaoException {
		if (shop.getCategoryId() != null) {
			shop.setCategory(m_shopCategoryDAO.get(shop.getCategoryId()));
		}
	}

}

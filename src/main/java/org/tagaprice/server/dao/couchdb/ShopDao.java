package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.server.dao.couchdb.elasticsearch.QueryObject;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.BoundingBoxFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.FilteredQuery;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.MatchAllQuery;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ShopDao extends DaoClass<Shop> implements IShopDao {
	
	private ICategoryDao m_shopCategoryDAO;
	private ElasticSearchClient m_searchClient;
	
	public ShopDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Shop.class, "shop", daoFactory._getDocumentDao());
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
		QueryObject queryObject = new QueryObject().query(
			new FilteredQuery().query(
				new MatchAllQuery()
			).filter(
				new BoundingBoxFilter().fieldName(
					"address.pos"
				).boundingBox(
					new BoundingBoxFilter.BoundingBox().convert(bbox)
				)
			)
		).size(100);
		
		SearchResult result = m_searchClient.find(queryObject);
		return result.getHits().getIDs();
	}

	@Override
	protected void _injectFields(Shop shop) throws DaoException {
		if (shop.getCategoryId() != null) {
			shop.setCategory(m_shopCategoryDAO.get(shop.getCategoryId()));
		}
	}

}

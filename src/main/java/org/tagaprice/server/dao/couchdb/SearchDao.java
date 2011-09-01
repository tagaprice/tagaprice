package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONParser;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.Hit;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class SearchDao implements ISearchDao {
	private ElasticSearchClient m_searchClient = null;
	
	public SearchDao(CouchDbDaoFactory daoFactory) {
		m_searchClient = daoFactory.getElasticSearchClient();
	}
	
	@Override
	public List<Document> search(String query, BoundingBox bbox) throws DaoException {
		return search(query, 10);
	}
	
	public List<Document> search(String query, int limit) throws DaoException {
		List<Document> rc = new ArrayList<Document>();

		SearchResult searchResult = m_searchClient.find(query, limit);

		for (Hit hit: searchResult.getHits().getHits()) {
			/// TODO find a way to avoid calling get() here (we should be able to use hit.getSource() directly)
			String json = org.svenson.JSON.defaultJSON().forValue(hit.getSource());
			Document item = JSONParser.defaultJSONParser().parse(Document.class, json);
			if (item != null) rc.add(item);
		}

		return rc;
	}

}

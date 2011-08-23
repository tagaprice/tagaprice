package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONParser;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.couchdb.elasticsearch.ElasticSearchClient;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.Hit;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class SearchDao implements ISearchDao {
	private ElasticSearchClient m_searchClient = null;
	
	@Override
	public List<Document> search(String query) throws DaoException {
		return search(query, 10);
	}
	
	public List<Document> search(String query, int limit) throws DaoException {
		List<Document> rc = new ArrayList<Document>();

		if (m_searchClient == null) {
			try {
				m_searchClient = new ElasticSearchClient(CouchDbDaoFactory.getConfiguration());
			}
			catch (IOException e) {
				throw new DaoException("Error while fetching the database configuration!", e);
			}
		}

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

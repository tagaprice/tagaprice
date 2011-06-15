package org.tagaprice.server.dao.couchdb.elasticsearch;

import org.jcouchdb.db.Response;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.couchdb.CouchDbConfig;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.TermFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Filtered;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryString;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Term;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import com.allen_sauer.gwt.log.client.Log;

public class ElasticSearchClient {
	/// Logger instance

	// we'll simply use CouchDB's ServerImpl here (it provides a simple way to query via HTTP and fits our purpose)
	private Server m_server;

	private String m_queryUrl;

	public ElasticSearchClient(CouchDbConfig configuration) {
		String host = configuration.getElasticSearchHost();
		int port = configuration.getElasticSearchPort();
		String indexName = configuration.getElasticSearchIndex();
		Log.debug("Connecting to ElasticSearch server at "+host+":"+port);
		m_server = new ServerImpl(host, port);

		m_queryUrl = "/"+indexName+"/_search";
		_inject(indexName, configuration);
	}

	/**
	 * This method checks if the elasticsearch river-couchdb is set up properly and does that if necessary
	 * @param indexName elasticsearch index name
	 * @param configuration the rest of the couchdb configuration
	 */
	private void _inject(String indexName, CouchDbConfig configuration) {
		String indexMetaUrl = "/_river/"+indexName+"/_meta";
		// first check if the index already exists:
		Response response = m_server.get(indexMetaUrl);
		if (response.getCode() == 404) {
			Log.debug("Didn't find elasticsearch index, creating it...");

			/// TODO move this data to an external file
			String couchHost = configuration.getCouchHost();
			int couchPort = configuration.getCouchPort();
			String couchDb = configuration.getCouchDatabase();

			String indexJson = "{\n"
				+ "  \"type\": \"couchdb\",\n"
				+ "  \"couchdb\": {\n"
				+ "    \"host\": \""+couchHost+"\",\n"
				+ "    \"port\": "+couchPort+",\n"
				+ "    \"db\": \""+couchDb+"\",\n"
				+ "    \"filter\": null\n"
				+ "  }\n"
				+ "}";

			response = m_server.put(indexMetaUrl, indexJson);

			int responseCode = response.getCode();
			if (responseCode >= 200 && responseCode <= 299) Log.debug("Index successfully created (HTTP response code "+responseCode+")");
			else {
				Log.debug("Failed creating index (HTTP response code "+responseCode+")");
				Log.debug("Error information: "+response.getContentAsString());

			}
		}
	}

	public SearchResult find(String query, int limit) {
		QueryObject queryObject = new QueryObject(new QueryString(query), 0, limit);
		return find(queryObject);
	}

	public SearchResult find(String query, String entityType, int limit) {
		QueryObject queryObject = new QueryObject(
				new Filtered(
						new TermFilter(
								new Term("entityType", entityType)
						),
						new QueryString(query)
				), 0, limit
		);
		return find(queryObject);
	}

	public SearchResult find(QueryObject queryObject) {
		String json = JSON.defaultJSON().forValue(queryObject);

		Response response = m_server.post(m_queryUrl, json);
		String jsonResult = response.getContentAsString();

		return JSONParser.defaultJSONParser().parse(SearchResult.class, jsonResult);
	}
}

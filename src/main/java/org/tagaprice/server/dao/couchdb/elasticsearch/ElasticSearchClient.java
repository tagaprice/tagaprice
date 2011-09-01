package org.tagaprice.server.dao.couchdb.elasticsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.jcouchdb.db.Response;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.couchdb.CouchDbConfig;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.TermFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.FilteredQuery;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryString;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Term;
import org.tagaprice.server.dao.couchdb.elasticsearch.result.SearchResult;
import com.allen_sauer.gwt.log.client.Log;

public class ElasticSearchClient {
	/// Logger instance

	// we'll simply use CouchDB's ServerImpl here (it provides a simple way to query via HTTP and fits our purpose)
	private Server m_server;

	private String m_queryUrl;

	public ElasticSearchClient(CouchDbConfig configuration) throws IOException, URISyntaxException {
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
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	private void _inject(String indexName, CouchDbConfig configuration) throws IOException, URISyntaxException {
		String indexMetaUrl = "/_river/"+indexName+"/_meta";
		// first check if the index already exists:
		Response response = m_server.get(indexMetaUrl);
		if (response.getCode() == 404) {
			response.destroy();
			Log.debug("Didn't find elasticsearch index, creating it...");
			
			// first create the empty ES index
			response = m_server.put("/"+indexName);
			response.destroy();

			// import the mapping file
			String mappingJson = _getResourceData("mapping.json");
			response = m_server.put(indexName+"/"+indexName+"/_mapping", mappingJson);
			response.destroy();

			// then PUT the river
			String riverJson = _getResourceData("river.json");
			riverJson = riverJson.replace("{COUCH_HOST}", configuration.getCouchHost());
			riverJson = riverJson.replace("{COUCH_PORT}", new Integer(configuration.getCouchPort()).toString());
			riverJson = riverJson.replace("{COUCH_DB}", configuration.getCouchDatabase());
			response = m_server.put(indexMetaUrl, riverJson);

			int responseCode = response.getCode();
			if (responseCode >= 200 && responseCode <= 299) Log.debug("Index successfully created (HTTP response code "+responseCode+")");
			else {
				Log.debug("Failed creating index (HTTP response code "+responseCode+")");
				Log.debug("Error information: "+response.getContentAsString());

			}
		}
	}
	
	public String _getResourceData(String filename) throws IOException, URISyntaxException {
		String fullFn = "/elasticsearch/"+filename;
		URL url = getClass().getClassLoader().getResource(fullFn);
		if (url == null) throw new FileNotFoundException("Couldn't load resource: '"+fullFn+"'");
		File file = new File(url.toURI());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String rc = "";
		while (reader.ready()) {
			rc += reader.readLine();
		}
		return rc;
	}

	public SearchResult find(String query, int limit) {
		QueryObject queryObject = new QueryObject(new QueryString(query), 0, limit);
		return find(queryObject);
	}

	public SearchResult find(String query, String docType, int limit) {
		QueryObject queryObject = new QueryObject(
				new FilteredQuery(
						new QueryString(query),
						new TermFilter(
								new Term("docType", docType)
						)
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

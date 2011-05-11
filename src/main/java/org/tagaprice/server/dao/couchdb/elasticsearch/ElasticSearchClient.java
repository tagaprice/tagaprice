package org.tagaprice.server.dao.couchdb.elasticsearch;

import java.util.Properties;

import org.jcouchdb.db.Response;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.TermFilter;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Filtered;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryString;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Term;

public class ElasticSearchClient {
	private static final String DEFAULT_HOST = "localhost";
	private static final String DEFAULT_PORT = "9200";
	private static final String DEFAULT_INDEXNAME = "tagaprice";
	
    // we'll simply use CouchDB's ServerImpl here (it provides a simple way to query via HTTP and fits our purpose)
    private Server m_server;
    
    private String m_queryUrl;

    public ElasticSearchClient(Properties configuration) {
    	String host = configuration.getProperty("elasticSearch.host", DEFAULT_HOST);
    	int port = Integer.parseInt(configuration.getProperty("elasticSearch.port", DEFAULT_PORT));
    	String indexName = configuration.getProperty("elasticSearch.index", DEFAULT_INDEXNAME);
    	m_server = new ServerImpl(host, port);
    	
    	m_queryUrl = "/"+indexName+"/_search";
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
		
		return (SearchResult) JSONParser.defaultJSONParser().parse(SearchResult.class, jsonResult);
	}
}

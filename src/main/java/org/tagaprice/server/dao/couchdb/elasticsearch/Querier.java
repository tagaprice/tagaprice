package org.tagaprice.server.dao.couchdb.elasticsearch;

import org.jcouchdb.db.Response;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryString;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryWrapper;

public class Querier {
    // we'll simply use CouchDB's ServerImpl here (it provides a simple way to query via HTTP and fits our purpose)
    private Server m_server;

    public Querier() {
    	m_server = new ServerImpl("localhost", 9200);
    }

	public void find(String query, int limit) {
		QueryObject queryObject = new QueryObject(new QueryWrapper(new QueryString(query)), 0, limit);
		find(queryObject);
	}
	
	public void find(QueryObject queryObject) {
		String json = JSON.defaultJSON().forValue(queryObject);
		System.out.println("JSON: "+json);
		
		Response response = m_server.post("/tagaprice/_search", json);
		String jsonResult = response.getContentAsString();
		System.out.println("Response:");
		System.out.println(jsonResult);
		
		SearchResult searchResult = (SearchResult) JSONParser.defaultJSONParser().parse(SearchResult.class, jsonResult);
		System.out.println(searchResult.toString());
	}

	public static void main(String args[]) {
		Querier querier = new Querier();
		querier.find("Merkur Donaueschingenstraße", 50);
		querier.find("Merkur Markt", 50);
	}
}

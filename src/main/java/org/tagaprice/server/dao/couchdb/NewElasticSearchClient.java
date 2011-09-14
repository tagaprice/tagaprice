package org.tagaprice.server.dao.couchdb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class NewElasticSearchClient {
	private Client m_client;
	private String m_indexName;

	public NewElasticSearchClient() {
		m_client = new TransportClient()
			.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		m_indexName = "tagaprice";
	}
	
	public SearchResponse find(Document.Type type, String query, int from, int size) {
		QueryBuilder queryBuilder = queryString(query);

		return find(type, queryBuilder, from, size);
	}
	
	public SearchResponse find(Document.Type type, QueryBuilder queryBuilder) {
		return find(type, queryBuilder, 0, 10);
	}
	
	public SearchResponse find(Document.Type type, QueryBuilder queryBuilder, int from, int size) {
		String indexString = m_indexName;
		if (type != null) indexString += "/"+type;
		return m_client.prepareSearch(indexString)
			.setQuery(queryBuilder)
			.setFrom(from)
			.setSize(size)
			.execute()
			.actionGet();
	}
	
	public SearchResponse findProduct(String query, int limit) {
		return find(Document.Type.PRODUCT, query, 0, limit);
	}
	
	public SearchResponse findShop(String query, BoundingBox bbox, int limit) {
		QueryBuilder queryBuilder = filteredQuery(
			queryString(query),
			geoBoundingBoxFilter("address.pos")
				.bottomRight(bbox.getSouthLat(), bbox.getEastLon())
				.topLeft(bbox.getNorthLat(), bbox.getWestLon())
		);

		return find(Document.Type.SHOP, queryBuilder, 0, limit);
	}

}

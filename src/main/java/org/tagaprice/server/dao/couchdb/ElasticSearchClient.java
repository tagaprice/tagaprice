package org.tagaprice.server.dao.couchdb;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class ElasticSearchClient {
	private Client m_client;
	private String m_indexName;

	public ElasticSearchClient() {
		m_client = new TransportClient()
			.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		m_indexName = "tagaprice";
	}
	
	public SearchResponse find(String query, int from, int size, Document.Type ... types) {
		QueryBuilder queryBuilder = queryString(query);

		return find(queryBuilder, from, size, types);
	}
	
	public SearchResponse find(String query, BoundingBox bbox, int start, int limit, Document.Type ... types) {
		QueryBuilder queryBuilder = filteredQuery(
				queryString(query),
				orFilter(
					notFilter(
						termFilter("docType", "shop")
					),
					geoBoundingBoxFilter("address.pos")
						.bottomRight(bbox.getSouthLat(), bbox.getEastLon())
						.topLeft(bbox.getNorthLat(), bbox.getWestLon())
				)
			);
            return find(queryBuilder, start, limit, types);
       }

	
	public SearchResponse find(QueryBuilder queryBuilder, Document.Type ... types) {
		return find(queryBuilder, 0, 10, types);
	}
	
	public SearchResponse find(QueryBuilder queryBuilder, int from, int size, Document.Type ... types) {
		SearchRequestBuilder searchBuilder = m_client.prepareSearch(m_indexName)
			.setQuery(queryBuilder)
			.setFrom(from)
			.setSize(size);

		if (types.length > 0) {
			String typeStrings[] = new String[types.length];

			int i = 0;
			for (Document.Type type: types) {
				typeStrings[i++] = type.toString();
			}

			searchBuilder.setTypes(typeStrings);
		}

		return searchBuilder
			.execute()
			.actionGet();
	}
	
	public SearchResponse findProduct(String query, int limit) {
		return find(query, 0, limit, Document.Type.PRODUCT);
	}
	
	public SearchResponse findShop(String query, BoundingBox bbox, int limit) {
		QueryBuilder queryBuilder = filteredQuery(
			queryString(query),
			geoBoundingBoxFilter("address.pos")
				.bottomRight(bbox.getSouthLat(), bbox.getEastLon())
				.topLeft(bbox.getNorthLat(), bbox.getWestLon())
		);

		return find(queryBuilder, 0, limit, Document.Type.SHOP);
	}

}

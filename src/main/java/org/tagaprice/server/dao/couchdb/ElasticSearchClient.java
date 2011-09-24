package org.tagaprice.server.dao.couchdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.action.search.SearchRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.GeoBoundingBoxFilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.tagaprice.server.rpc.Mail;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;

import com.allen_sauer.gwt.log.client.Log;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class ElasticSearchClient {
	private Client m_client;
	private String m_indexName;

	public ElasticSearchClient(CouchDbConfig config, String configDir) throws IOException {
		Log.debug("Connecting to the ElasticSearch server (host: '"+config.getElasticSearchHost()+"', port: "+config.getElasticSearchPort()+")");
		m_client = new TransportClient()
			.addTransportAddress(new InetSocketTransportAddress(
				config.getElasticSearchHost(),
				config.getElasticSearchPort())
			);
		m_indexName = config.getElasticSearchIndex();
		
		// create the index if necessary
		createIndexIfNotExists(m_indexName);
		
		// create the mappings
		for (String fileName: InitialInjector.allFilesIn("elasticsearch/"+configDir+"/mappings/")) {
			if (fileName.endsWith(".json")) {
				File file = new File(fileName);
				String typeName = file.getName().replace(".json", "");
				String data = getInputStreamContents(new FileInputStream(file));
				createDocumentIfNotExists(m_indexName, typeName, "_mapping", data);
			}
		}

		// check if the _river index exists
		createIndexIfNotExists("_river");
		
		// create the river configuration
		String riverConfig = getInputStreamContents(Mail.loadFile("elasticsearch/"+configDir+"/river.json"));
		
		riverConfig = riverConfig
			.replace("{COUCH_HOST}", config.getCouchHost())
			.replace("{COUCH_PORT}", new Integer(config.getCouchPort()).toString())
			.replace("{COUCH_DB}", config.getCouchDatabase());
		createDocumentIfNotExists("_river", m_indexName, "_meta", riverConfig);
	}
	
	private String getInputStreamContents(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer rc = new StringBuffer();
		
		while ((line = reader.readLine()) != null) {
			rc.append(line);
		}
		return rc.toString();
	}
	
	private void createIndexIfNotExists(String indexName) {
		IndicesExistsResponse existsResponse = m_client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();
		if (!existsResponse.exists()) {
			CreateIndexResponse createResponse = m_client.admin().indices().create(new CreateIndexRequest(indexName)).actionGet();
			if (!createResponse.acknowledged()) throw new RuntimeException("Index creation failed (indexName: '"+indexName+"')");
		}
	}
	
	private void createDocumentIfNotExists(String indexName, String type, String id, String data) {
		// TODO check if the document already exists
		m_client.prepareIndex(indexName, type, id)
			.setRouting("meta")
			.setSource(data).execute().actionGet();
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
					createBoundingBoxFilter("address.pos", bbox)
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
			createBoundingBoxFilter("address.pos", bbox)
		);

		return find(queryBuilder, 0, limit, Document.Type.SHOP);
	}
	
	public static GeoBoundingBoxFilterBuilder createBoundingBoxFilter(String name, BoundingBox bbox) {
		return geoBoundingBoxFilter(name)
			.bottomRight(bbox.getSouthLat(), bbox.getEastLon())
			.topLeft(bbox.getNorthLat(), bbox.getWestLon());
	}
}

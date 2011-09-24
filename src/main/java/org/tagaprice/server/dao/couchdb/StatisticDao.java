package org.tagaprice.server.dao.couchdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.svenson.JSON;
import org.svenson.JSONParser;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.server.dao.couchdb.statisticAggregator.StatisticAggregator;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.dao.DaoException;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {
	private ElasticSearchClient m_searchClient;
	private StatisticAggregator m_statisticAggregator;

	public StatisticDao(CouchDbDaoFactory daoFactory) throws IOException {
		super(daoFactory, StatisticResult.class, Document.Type.STATISTICS, null);

		String statisticsDb = "tagaprice-statistics";

		if (!m_server.listDatabases().contains(statisticsDb)) {
			new InitialInjector().init(m_server, statisticsDb, "statistics");
		}

		m_db = new Database(m_server, statisticsDb);

		CouchDbConfig config;
		try {
			config = new CouchDbConfig(CouchDbDaoFactory.getConfiguration());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error while reading CouchDB configuration: "+e.getMessage(), e);
		}
		
		config.setProperty("elasticSearch.index", statisticsDb);
		m_searchClient = new ElasticSearchClient(config, "statistics");
		m_statisticAggregator = new StatisticAggregator(daoFactory, this);
		m_statisticAggregator.start();
	}

	@Override
	protected void _checkCreatorId(String creatorId) throws DaoException {
		if (creatorId != null) throw new DaoException("The creator ID of StatisticResult objects has to be null!");
	}

	public void delete(Map<String, String> deletedIDs) {
		Document deleteArray[] = new Document[deletedIDs.size()];

		int i = 0;
		for(String id: deletedIDs.keySet()) {
			String rev = deletedIDs.get(id);
			deleteArray[i++] = new Document(null, id, rev, null);
		}

		delete(deleteArray);
	}
	
	public List<StatisticResult> getAffected(String documentId) throws DaoException {
		List<StatisticResult> rc = new ArrayList<StatisticResult>();
		for (String affectedId: getAffectedIDs(documentId).keySet()) {
			rc.add(get(affectedId));
		}
		return rc;
	}
	
	/**
	 * Returns a list of the IDs of all StatisticResult objects that are referencing the given document ID
	 *	and therefore might be affected by changes to that document 
	 * @param documentId ID of the Document to find referencing StatisticResults 
	 * @return Map of the matching StatisticResult IDs and their current revisions (for simple deletion)
	 * @throws DaoException If there were problems fetching the {@link StatisticResult} objects
	 */
	public Map<String, String> getAffectedIDs(String documentId) throws DaoException {
		Map<String, String> rc = new HashMap<String, String>();

		ViewResult<?> result = m_db.queryView("statistics/containedIDs", StatisticResult.class, new Options().key(documentId), null);

		for (ValueRow<?> row: result.getRows()) {
			String statisticsId = row.getId();
			rc.put(statisticsId, row.getValue().toString());
		}

		return rc;
	}
	
	public Map<String, String> getIDsByReceipt(String receiptId) {
		Map<String, String> rc = new HashMap<String, String>();

		ViewResult<?> result = m_db.queryView("statistics/byReceipt", StatisticResult.class, new Options().key(receiptId), null);

		for (ValueRow<?> row: result.getRows()) {
			String statisticsId = row.getId();
			rc.put(statisticsId, row.getValue().toString());
		}

		return rc;
	}
	
	public long getMaxSequenceNr() throws DaoException {
		long rc = 0;

		ViewResult<?> result = m_db.queryView("statistics/sequenceNrs", StatisticResult.class, new Options().descending(true).limit(1), null);

		for (ValueRow<?> row: result.getRows()) {
			rc = new Long(row.getKey().toString());
		}

		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException {
		List<StatisticResult> rc = new ArrayList<StatisticResult>();
		
		QueryBuilder queryBuilder = filteredQuery(
			matchAllQuery(),
			andFilter(
				termFilter("product._id", productId),
				ElasticSearchClient.createBoundingBoxFilter("address.pos", bbox),
				rangeFilter("timestamp").from(begin.getTime()).to(end.getTime())
			)
		);
		
		SearchResponse searchResponse = m_searchClient.find(queryBuilder, Document.Type.STATISTICS);

		for (SearchHit hit: searchResponse.getHits().getHits()) {
			StatisticResult statistic = JSONParser.defaultJSONParser().parse(StatisticResult.class, JSON.defaultJSON().forValue(hit.getSource()));
			rc.add(statistic);
		}

		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaShop(String shopId, Date begin, Date end) throws DaoException {
		List<StatisticResult> rc = new ArrayList<StatisticResult>();

		QueryBuilder queryBuilder = constantScoreQuery(
			andFilter(
				termFilter("shop._id", shopId),
				rangeFilter("timestamp").from(begin.getTime()).to(end.getTime())
			)
		);

		SearchResponse searchResponse = m_searchClient.find(queryBuilder, Document.Type.STATISTICS);

		for (SearchHit hit: searchResponse.getHits().getHits()) {
			StatisticResult statistic = JSONParser.defaultJSONParser().parse(StatisticResult.class, JSON.defaultJSON().forValue(hit.getSource()));
			rc.add(statistic);
		}

		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaShopCategory(String categoryId,
			BoundingBox bbox, Date begin, Date end) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatisticResult> searchPricesViaProductCategory(
			String categoryId, BoundingBox bbox, Date begin, Date end)
			throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}

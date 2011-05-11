package org.tagaprice.server.dao.couchdb.elasticsearch;

import org.svenson.JSONProperty;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Query;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryWrapper;

public class QueryObject {
	private Integer m_from;
	private Integer m_size;
	private Query m_query;
	
	public QueryObject(Query query, Integer from, Integer size) {
		m_query = query;
		m_from = from;
		m_size = size;
	}
	
	@JSONProperty(ignore=true)
	public Query getQuery() {
		return m_query;
	}
	
	@JSONProperty("query")
	public QueryWrapper getQueryWrapper() {
		return new QueryWrapper(m_query);
	}
	
	@JSONProperty(ignoreIfNull=true)
	public int getFrom() {
		return m_from;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Integer getSize() {
		return m_size;
	}
}

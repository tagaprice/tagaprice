package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;

public class QueryObject {
	private Integer m_from;
	private Integer m_size;
	private Query m_query;
	
	public QueryObject(Query query, Integer from, Integer size) {
		m_query = query;
		m_from = from;
		m_size = size;
	}
	
	public Query getQuery() {
		return m_query;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public int getFrom() {
		return m_from;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Integer getSize() {
		return m_size;
	}
	
	public static class Query {
		QueryString m_queryString;
		
		public Query(QueryString queryString) {
			m_queryString = queryString;
		}
		
		@JSONProperty(value="query_string")
		public QueryString getQueryString() {
			return m_queryString;
		}
	}
}

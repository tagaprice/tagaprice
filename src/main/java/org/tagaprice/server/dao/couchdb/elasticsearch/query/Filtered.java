package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.Filter;

public class Filtered implements Query {
	private Query m_query;
	private Filter m_filter;
	
	public Filtered(Filter filter, Query query) {
		m_filter = filter;
		m_query = query;
	}
	
	@JSONProperty(ignore=true)
	public Query getQuery() {
		return m_query;
	}
	
	@JSONProperty("query")
	public QueryWrapper getQueryWrapper() {
		return new QueryWrapper(m_query);
	}
	
	public Filter getFilter() {
		return m_filter;
	}

	@Override
	public String queryType() {
		return "filtered";
	}
}

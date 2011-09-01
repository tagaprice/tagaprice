package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;
import org.tagaprice.server.dao.couchdb.elasticsearch.filter.Filter;

public class FilteredQuery implements Query {
	private Query m_query;
	private Filter m_filter;
	
	public FilteredQuery() {
	}

	public FilteredQuery query(Query query) {
		m_query = query;
		return this;
	}
	
	public FilteredQuery filter(Filter filter) {
		m_filter = filter;
		return this;
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

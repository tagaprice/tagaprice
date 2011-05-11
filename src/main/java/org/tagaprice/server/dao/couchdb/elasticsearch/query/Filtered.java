package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.tagaprice.server.dao.couchdb.elasticsearch.filter.Filter;

public class Filtered implements Query {
	private QueryWrapper m_query;
	private Filter m_filter;
	
	public Filtered(Filter filter, QueryWrapper query) {
		m_filter = filter;
		m_query = query;
	}
	
	public QueryWrapper getQuery() {
		return m_query;
	}
	
	public Filter getFilter() {
		return m_filter;
	}

	@Override
	public String queryType() {
		return "filtered";
	}
}

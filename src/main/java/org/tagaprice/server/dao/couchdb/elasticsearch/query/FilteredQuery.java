package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.tagaprice.server.dao.couchdb.elasticsearch.filter.Filter;

public class FilteredQuery {
	private Query m_query;
	private Filter m_filter;
	
	public FilteredQuery(Filter filter, Query query) {
		m_filter = filter;
		m_query = query;
	}
}

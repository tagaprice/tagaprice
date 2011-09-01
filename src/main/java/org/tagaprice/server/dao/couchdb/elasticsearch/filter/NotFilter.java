package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import org.svenson.JSONProperty;

public class NotFilter implements Filter {
	private Filter m_child;
	
	public NotFilter() {
	}
	
	public NotFilter setChild(Filter filter) {
		m_child = filter;
		return this;
	}
	
	@JSONProperty(value="not")
	public Filter getChild() {
		return m_child;
	}
}

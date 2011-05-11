package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import java.util.Set;
import java.util.TreeSet;

import org.svenson.DynamicProperties;
import org.svenson.JSONProperty;

public class QueryWrapper implements DynamicProperties {
	private Query m_query;
	
	public QueryWrapper(Query query) {
		m_query = query;
	}
	
	@JSONProperty(ignore=true)
	public Query getQuery() {
		return m_query;
	}

	@Override
	public void setProperty(String name, Object value) {
		// do nothing
	}

	@Override
	public Object getProperty(String name) {
		Query rc = null;
		if (m_query.queryType().equals(name)) rc = m_query;
		return rc;
	}

	@Override
	public Set<String> propertyNames() {
		Set<String> rc = new TreeSet<String>();
		rc.add(m_query.queryType());
		return rc;
	}
}

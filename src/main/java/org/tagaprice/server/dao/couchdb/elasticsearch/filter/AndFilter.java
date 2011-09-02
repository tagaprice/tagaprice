package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONProperty;

public class AndFilter implements Filter {
	private List<Filter> m_conditions = new ArrayList<Filter>();

	public AndFilter addCondition(Filter condition) {
		m_conditions.add(condition);
		return this;
	}
	
	@JSONProperty(value="and")
	public List<Filter> getConditions() {
		return m_conditions;
	}
}

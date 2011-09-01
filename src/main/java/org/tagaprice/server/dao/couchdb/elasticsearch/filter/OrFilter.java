package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONProperty;

public class OrFilter implements Filter {
	private List<Filter> m_conditions = new ArrayList<Filter>();

	public OrFilter addCondition(Filter condition) {
		m_conditions.add(condition);
		return this;
	}
	
	@JSONProperty(value="or")
	public List<Filter> getConditions() {
		return m_conditions;
	}
}

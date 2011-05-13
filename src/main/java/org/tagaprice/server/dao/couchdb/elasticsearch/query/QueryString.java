package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;
import org.tagaprice.server.dao.couchdb.elasticsearch.exception.QueryException;

public class QueryString implements Query {
	private String m_query;
	private QueryString.DefaultOperator m_defaultOperator;		
	
	public QueryString(String query, QueryString.DefaultOperator defaultOperator) {
		if (query == null || query.isEmpty()) throw new QueryException("Query string must not be empty!");
		m_query = query;
		m_defaultOperator = defaultOperator;
	}
	
	public QueryString(String query) {
		this(query, DefaultOperator.AND);
	}
	
	@JSONProperty(value="default_operator", ignoreIfNull=true)
	public String getDefaultOperator() {
		return m_defaultOperator.value();
	}

	public String getQuery() {
		return m_query;
	}
	
	public enum DefaultOperator {
		OR(null), AND("AND");
		
		private final String value;
		
		DefaultOperator(String value) {
			this.value = value;
		}
		
		public String value() {
			return this.value;
		}
	}

	@Override
	public String queryType() {
		return "query_string";
	}

}
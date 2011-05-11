package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import org.svenson.JSONProperty;

public class QueryString {
	private String m_query;
	private QueryString.DefaultOperator m_defaultOperator;		
	
	public QueryString(String query, QueryString.DefaultOperator defaultOperator) {
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

}
package org.tagaprice.server.dao.couchdb.elasticsearch;

import org.svenson.JSONProperty;

public class QueryObject {
	private Integer m_from;
	private Integer m_size;
	private Query m_query;
	
	public QueryObject(Query query, Integer from, Integer size) {
		m_query = query;
		m_from = from;
		m_size = size;
	}
	
	public Query getQuery() {
		return m_query;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public int getFrom() {
		return m_from;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Integer getSize() {
		return m_size;
	}
	
	public static class Query {
		QueryString m_queryString;
		
		public Query(QueryString queryString) {
			m_queryString = queryString;
		}
		
		@JSONProperty(value="query_string")
		public QueryString getQueryString() {
			return m_queryString;
		}
	}
	
	public static class QueryString {
		private String m_query;
		private DefaultOperator m_defaultOperator;		
		
		public QueryString(String query, DefaultOperator defaultOperator) {
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
}

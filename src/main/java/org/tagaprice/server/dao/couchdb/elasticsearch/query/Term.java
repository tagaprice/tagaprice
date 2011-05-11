package org.tagaprice.server.dao.couchdb.elasticsearch.query;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.svenson.DynamicProperties;
import org.svenson.JSONProperty;


public class Term implements DynamicProperties {
	// we only want to store one property at a time
	private String m_propertyName = null;
	private Object m_propertyValue = null;
	private BigDecimal m_boost = null;
	
	public Term(String name, String value, BigDecimal boost) {
		m_propertyName = name;
		m_propertyValue = value;
		m_boost = boost;
	}
	
	public Term(String name, String value) {
		this(name, value, null);
	}
	
	@Override
	public void setProperty(String name, Object value) {
		m_propertyName = name;
		m_propertyValue = value;
	}

	@Override
	public Object getProperty(String name) {
		Object rc = null;
		if (m_propertyName.equals(name)) rc = m_propertyValue;
		return rc;
	}

	@Override
	public Set<String> propertyNames() {
		Set<String> rc = new TreeSet<String>();
		
		if (m_propertyName != null && !m_propertyName.isEmpty()) {
			rc.add(m_propertyName);
		}
		return rc;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public BigDecimal getBoost() {
		return m_boost;
	}
}

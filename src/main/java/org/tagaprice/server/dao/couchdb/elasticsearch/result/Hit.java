package org.tagaprice.server.dao.couchdb.elasticsearch.result;

import java.math.BigDecimal;
import java.util.Map;

import org.svenson.JSONProperty;

public class Hit {
	private String m_type;
	private Map<String, Object> m_fields;
	private Map<String, Object> m_source;
	private String m_index;
	private BigDecimal m_score;
	private String m_id;
	
	@JSONProperty(value="_id")
	public String getId() {
		return m_id;
	}
	
	public void setId(String id) {
		m_id = id;
	}

	@JSONProperty(value="_index")
	public String getIndex() {
		return m_index;
	}
	public void setIndex(String index) {
		m_index = index;
	}

	@JSONProperty(value="_score")
	public BigDecimal getScore() {
		return m_score;
	}
	public void setScore(BigDecimal score) {
		m_score = score;
	}

	@JSONProperty(value="_source")
	public Map<String, Object> getSource() {
		return m_source;
	}
	public void setSource(Map<String, Object> source) {
		m_source = source;
	}

	@JSONProperty(value="_type")
	public String getType() {
		return m_type;
	}
	public void setType(String type) {
		m_type = type;
	}
	
	@JSONProperty(value="fields")
	public Map<String, Object> getFields() {
		return m_fields;
	}
	
	public void setFields(Map<String, Object> fields) {
		m_fields = fields;
	}
	
	@JSONProperty(ignore=true)
	public Object getField(String name) {
		return m_fields.get(name);
	}
}
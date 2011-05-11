package org.tagaprice.server.dao.couchdb.elasticsearch;

import java.math.BigDecimal;
import java.util.Map;

import org.svenson.JSONProperty;

public class SearchResult {
	private int m_took = -1;
	private boolean m_timedOut = false;
	private Shards m_shards = null;
	private Hits m_hits = null;
	
	/**
	 * Returns the time in msec that the query took to execute on the ElasticSearch server
	 * @return time in milliseconds
	 */
	public int getTook() {
		return m_took;
	}
	
	public void setTook(int took) {
		m_took = took;
	}
	
	@JSONProperty(value="timed_out")
	public boolean isTimedOut() {
		return m_timedOut;
	}
	
	public void setTimedOut(boolean timed_out) {
		m_timedOut = timed_out;
	}
	
	@JSONProperty(value="_shards")
	public Shards getShards() {
		return m_shards;
	}
	
	public void setShards(Shards shards) {
		m_shards = shards;
	}
	
	public Hits getHits() {
		return m_hits;
	}
	
	public void setHits(Hits hits) {
		m_hits = hits;
	}
	
	public static class Shards {
		private int m_total, m_successful, m_failed;
		
		public int getTotal() {
			return m_total;
		}
		
		public void setTotal(int total) {
			m_total = total;
		}
		
		public int getSuccessful() {
			return m_successful;
		}
		
		public void setSuccessful(int successful) {
			m_successful = successful;
		}
		
		public int getFailed() {
			return m_failed;
		}
		
		public void setFailed(int failed) {
			m_failed = failed;
		}
	}
	
	public static class Hits {
		private int m_total;
		private Double m_maxScore;
		private Hit m_hits[];
		
		public int getTotal() {
			return m_total;
		}
		public void setTotal(int total) {
			m_total = total;
		}
		
		@JSONProperty(value="max_score")
		public Double getMaxScore() {
			return m_maxScore;
		}
		public void setMaxScore(Double maxScore) {
			m_maxScore = maxScore;
		}

		public Hit[] getHits() {
			return m_hits;
		}
		public void setHits(Hit hits[]) {
			m_hits = hits;
		}
	}
	
	public static class Hit {
		private String m_type;
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
	}
}

package org.tagaprice.server.dao.couchdb.elasticsearch.result;


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
}

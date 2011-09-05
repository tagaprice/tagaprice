package org.tagaprice.server.dao.couchdb.elasticsearch.result;

import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONProperty;

public class Hits {
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

	@JSONProperty(ignore=true)
	public List<String> getIDs() {
		List<String> rc = new ArrayList<String>();
		
		for (Hit hit: getHits()) {
			rc.add(hit.getId());
		}
		
		return rc;
	}
}
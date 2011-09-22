package org.tagaprice.server.dao.couchdb.statisticAggregator;

import org.tagaprice.shared.entities.Document;

public class CouchChange {
	private long _seq;
	private Long _lastSeq = null;
	private String _id;
	private boolean _deleted = false;
	private Document _document;
	
	public CouchChange() {}
	
	//
	// Getters
	//
	public Object getChanges() {
		return null; // the changes property only contains the revision which doesn't interest us
	}
	
	public Document getDoc() {
		return _document;
	}
	
	public String getId() {
		return _id;
	}

	public Long getLast_seq() {
		return _lastSeq;
	}
	
	public long getSeq() {
		return _seq;
	}
	
	public boolean getDeleted() {
		return _deleted;
	}
	
	public boolean hasLastSeq() {
		return _lastSeq != null;
	}
	
	
	//
	// Setters
	//
	public void setChanges(Object changes) {
		// do nothing
	}
	
	public void setDeleted(boolean deleted) {
		_deleted = deleted;
	}
	
	public void setDoc(Document document) {
		_document = document;
	}
	
	public void setId(String id) {
		_id = id;
	}
	
	public void setLast_seq(Long lastSeq) {
		_lastSeq = lastSeq;
		_seq = lastSeq;
	}
	
	public void setSeq(long seq) {
		_seq = seq;
	}
}

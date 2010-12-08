package org.tagaprice.core.beans;

import java.io.Serializable;

/**
 * TODO maybe refactor this to inner class of {@link EntityRevision}
 * @author "Harald Jagenteufel"
 *
 */
public class EntityRevisionId implements Serializable {
	private long _id;
	private int _revision;
	
	
	private EntityRevisionId() { }
	
	
	private void setId(long id) {
		_id = id;
	}
	
	private void setRevision(int revision) {
		_revision = revision;
	}
	
	public long getId() {
		return _id;
	}
	
	public int getRevision() {
		return _revision;
	}
}

package org.tagaprice.core.beans;

import java.util.Date;

public class EntityRevision {
	private EntityRevisionId _entityRevisionId;
	private String _title;
	private Date _createdAt;
	
	protected EntityRevision() { }
	
	protected EntityRevision(String title) {
		_title = title;
	}
	
	
	
	private void setEntityRevisionId(EntityRevisionId entityRevisionId) {
		_entityRevisionId = entityRevisionId;
	}

	private void setTitle(String title) {
		_title = title;
	}
	
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}

	public EntityRevisionId getEntityRevisionId() {
		return _entityRevisionId;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public Date getCreatedAt() {
		return _createdAt;
	}
	
	
}

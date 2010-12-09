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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_entityRevisionId == null) ? 0 : _entityRevisionId.hashCode());
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityRevision other = (EntityRevision) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_entityRevisionId == null) {
			if (other._entityRevisionId != null)
				return false;
		} else if (!_entityRevisionId.equals(other._entityRevisionId))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}
}

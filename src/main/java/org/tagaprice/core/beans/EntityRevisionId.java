package org.tagaprice.core.beans;

import java.io.Serializable;

/**
 * TODO maybe refactor this to inner class of {@link EntityRevision}
 * @author "Harald Jagenteufel"
 *
 */
@SuppressWarnings("serial")
public class EntityRevisionId implements Serializable {
	private long _id;
	private int _revision;


	private EntityRevisionId() { }


	@SuppressWarnings("unused")
	private void setId(long id) {
		_id = id;
	}

	@SuppressWarnings("unused")
	private void setRevision(int revision) {
		_revision = revision;
	}

	public long getId() {
		return _id;
	}

	public int getRevision() {
		return _revision;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_id ^ (_id >>> 32));
		result = prime * result + _revision;
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
		EntityRevisionId other = (EntityRevisionId) obj;
		if (_id != other._id)
			return false;
		if (_revision != other._revision)
			return false;
		return true;
	}
}

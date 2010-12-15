package org.tagaprice.core.entities;

import java.util.Date;

/**
 * Entity bean to map from the database using hibernate.
 * This class is immutable.
 * 
 * @author haja
 *
 */
public abstract class Entity {
	private Long _id = null;
	/** TODO remove this, needed by hibernate... */
	private Integer _currentRevisionNumber = null;
	private EntityRevision _currentRevision = null;
	private Date _createdAt = null;
	private Locale _locale = null;


	protected Entity() { }

	protected Entity(Locale locale, Date createdAt, int currentRevisionNumber) {
		_locale = locale;
		_createdAt = createdAt;
		_currentRevisionNumber = currentRevisionNumber;
	}



	//	public boolean isNew() {
	//		return (this.id == null);
	//	}

	public Locale getLocale() {
		return _locale;
	}


	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		_locale = locale;
	}


	public Long getId() {
		return _id;
	}

	/**
	 * this is used by hibernate to set the id through reflection
	 */
	@SuppressWarnings("unused")
	private void setId(Long id) {
		_id = id;
	}

	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return _createdAt;
	}

	public EntityRevision getCurrentRevision() {
		return _currentRevision;
	}

	public void setCurrentRevision(EntityRevision entityRevision) {
		_currentRevision = entityRevision;
	}

	@SuppressWarnings("unused")
	private void setCurrentRevisionNumber(Integer currentRevisionNumber) {
		_currentRevisionNumber = currentRevisionNumber;
	}

	public Integer getCurrentRevisionNumber() {
		return _currentRevisionNumber;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_currentRevision == null) ? 0 : _currentRevision.hashCode());
		result = prime * result + ((_currentRevisionNumber == null) ? 0 : _currentRevisionNumber.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((_locale == null) ? 0 : _locale.hashCode());
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
		Entity other = (Entity) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_currentRevision == null) {
			if (other._currentRevision != null)
				return false;
		} else if (!_currentRevision.equals(other._currentRevision))
			return false;
		if (_currentRevisionNumber == null) {
			if (other._currentRevisionNumber != null)
				return false;
		} else if (!_currentRevisionNumber.equals(other._currentRevisionNumber))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_locale == null) {
			if (other._locale != null)
				return false;
		} else if (!_locale.equals(other._locale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entity [_id=" + _id + ", _currentRevisionNumber=" + _currentRevisionNumber + ", _currentRevision="
		+ _currentRevision + ", _createdAt=" + _createdAt + ", _locale=" + _locale + "]";
	}
}

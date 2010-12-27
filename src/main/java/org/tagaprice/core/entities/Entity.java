package org.tagaprice.core.entities;

import java.util.Date;

/**
 * Entity bean to map from the database using hibernate.
 * This class is immutable.
 * 
 * @author haja
 *
 */
//@Entity
//@Table(name="entity")

public abstract class Entity {
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _currentRevisionNumber = null;
	private Account _creator = null;
	private Group _group;

	@Deprecated
	private EntityRevision _currentRevision = null;

	protected Entity() { }

	protected Entity(Long id, String title, Locale locale, Date createdAt, int currentRevisionNumber, Account creator, Group group) {
		_id = id;
		_title = title;
		_locale = locale;
		_createdAt = createdAt;
		_currentRevisionNumber = currentRevisionNumber;
		_creator = creator;
		_group = group;
	}



	public Long getId() {
		return _id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		_id = id;
	}

	public Locale getLocale() {
		return _locale;
	}

	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		_locale = locale;
	}

	public Date getCreatedAt() {
		return _createdAt;
	}

	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}

	public Integer getCurrentRevisionNumber() {
		return _currentRevisionNumber;
	}

	@SuppressWarnings("unused")
	private void setCurrentRevisionNumber(Integer currentRevisionNumber) {
		_currentRevisionNumber = currentRevisionNumber;
	}

	public Account getCreator() {
		return _creator;
	}

	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		_creator = creator;
	}


	@Deprecated
	public EntityRevision getCurrentRevision() {
		return _currentRevision;
	}

	@Deprecated
	public void setCurrentRevision(EntityRevision entityRevision) {
		_currentRevision = entityRevision;
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

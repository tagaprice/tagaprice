package org.tagaprice.core.beans;

import java.util.Date;

/**
 * Uses setter chaining for more details see http://weblogs.java.net/blog/emcmanus/archive/2010/10/25/using-builder-pattern-subclasses
 * @author "forste"
 *
 * @param <T>
 */
public abstract class Entity {
	private Long _id = null;
	/** TODO remove this, needed by hibernate... */
	private Integer _currentRevisionNumber = null;
	private EntityRevision _currentRevision = null;
	private Date _createdAt = null;
	private int _localeId;
	//	private Long _rev = null;



	protected Entity() { }
	
	
	protected Entity(int localeId, Date createdAt, int currentRevisionNumber) {
		_localeId = localeId;
		_createdAt = createdAt;
		_currentRevisionNumber = currentRevisionNumber;
	}
	
	


	public int getLocaleId() {
		return _localeId;
	}


	private void setLocaleId(int localeId) {
		_localeId = localeId;
	}
	

	public Long getId() {
		return _id;
	}

	/**
	 * this is used by hibernate to set the id through reflection
	 */
	@SuppressWarnings("unused")
	public void setId(Long id) {
		_id = id;
	}
	
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
		result = prime * result + ((_currentRevisionNumber == null) ? 0 : _currentRevisionNumber.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + _localeId;
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
		if (_localeId != other._localeId)
			return false;
		return true;
	}

	
	

	
	//	public Long getRev() {
	//		return _rev;
	//	}
	//
	//
	//	public void setRev(Long rev) {
	//		_rev = rev;
	//	}

}

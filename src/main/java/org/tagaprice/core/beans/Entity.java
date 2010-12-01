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
	//	private Long _rev = null;


	protected Entity() { }
	
	protected Entity(long id) {
		_id = id;
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


	
	//	public Long getRev() {
	//		return _rev;
	//	}
	//
	//
	//	public void setRev(Long rev) {
	//		_rev = rev;
	//	}

}

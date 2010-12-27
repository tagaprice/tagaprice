package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity bean to map from the database using hibernate.
 * This class is immutable.
 * 
 * @author haja
 *
 */
@javax.persistence.Entity
@Table(name="entityrevision")
@SecondaryTable(name="entity", pkJoinColumns={
		@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="ent_id")
})
public abstract class Entity {
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _revisionNumber = null;
	private Account _creator = null;
	private Group _group;

	protected Entity() { }

	protected Entity(Long id, String title, Locale locale, Date createdAt, int currentRevisionNumber, Account creator, Group group) {
		_id = id;
		setTitle(title);
		_locale = locale;
		_createdAt = createdAt;
		_revisionNumber = currentRevisionNumber;
		_creator = creator;
		setGroup(group);
	}

	@Id
	@Column(name="ent_id")
	public Long getId() {
		return _id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		_id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return _title;
	}
	private void setTitle(String title) {
		_title = title;
	}

	@ManyToOne
	@JoinColumn(name = "locale_id")
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		_locale = locale;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}

	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return _revisionNumber;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		_revisionNumber = revisionNumber;
	}

	@ManyToOne
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		_creator = creator;
	}

	@Transient
	public Group getGroup() {
		return _group;
	}
	private void setGroup(Group group) {
		_group = group;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_revisionNumber == null) ? 0 : _revisionNumber.hashCode());
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
		if (_revisionNumber == null) {
			if (other._revisionNumber != null)
				return false;
		} else if (!_revisionNumber.equals(other._revisionNumber))
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
		return "Entity [_id=" + _id + ", _currentRevisionNumber=" + _revisionNumber + ", _createdAt=" + _createdAt + ", _locale=" + _locale + "]";
	}

}

package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Represents a versionable entity, i.e. encompasses basic versionable data. When versioning, branching is not allowed, i.e. the only
 * branch is master.
 * In other words this class represents a revision of any (business) entity. Subclasses may provide additional information.
 * 
 * @author haja
 *
 */
//@javax.persistence.Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name="entityrevision")
//@SecondaryTable(name="entity", pkJoinColumns={
//		@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="ent_id")
//})
public abstract class RevisionableEntity {
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _revisionNumber = null;
	private Account _creator = null;
	private Group _group;

	protected RevisionableEntity() { }

	protected RevisionableEntity(Long id, String title, Locale locale, Date createdAt, int revisionNumber, Account creator, Group group) {
		this._id = id;
		this._title = title;
		this._locale = locale;
		this._createdAt = createdAt;
		this._revisionNumber = revisionNumber;
		this._creator = creator;
		this._group = group;
	}

	@Id
	@Column(name="ent_id")
	public Long getId() {
		return _id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this._id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return _title;
	}
	@SuppressWarnings("unused")
	private void setTitle(String title) {
		this._title = title;
	}

	@ManyToOne
	@JoinColumn(table="entity", name = "locale_id", referencedColumnName="locale_id")
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return _revisionNumber;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		this._revisionNumber = revisionNumber;
	}

	@ManyToOne
	@JoinColumn(table="entity", name = "creator")
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this._creator = creator;
	}

	@Transient
	public Group getGroup() {
		return _group;
	}
	@SuppressWarnings("unused")
	private void setGroup(Group group) {
		this._group = group;
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
		RevisionableEntity other = (RevisionableEntity) obj;
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

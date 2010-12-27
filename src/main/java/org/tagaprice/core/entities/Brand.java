package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Table(name="entityrevision")
@SecondaryTables({
	@SecondaryTable(name="entity", pkJoinColumns= {@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="ent_id")})
	,
	@SecondaryTable(name="brand", pkJoinColumns= {@PrimaryKeyJoinColumn(name="ent_id", referencedColumnName="brand_id")})

})
public class Brand extends NonRevisionableEntity {
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _revisionNumber = null;
	private Account _creator = null;
	private Group _group;

	protected Brand() { }

	protected Brand(Long id, String title, Locale locale, Date createdAt, int revisionNumber, Account creator, Group group) {
		this._id = id;
		this._title = title;
		this._locale = locale;
		this._createdAt = createdAt;
		this._revisionNumber = revisionNumber;
		this._creator = creator;
		this._group = group;
	}

	@Override
	@Id
	@Column(name="ent_id")
	public Long getId() {
		return _id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this._id = id;
	}

	@Override
	@Column(name="title")
	public String getTitle() {
		return _title;
	}
	@SuppressWarnings("unused")
	private void setTitle(String title) {
		this._title = title;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "locale_id")
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@Override
	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	@Override
	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return 0;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		return;
	}

	@Override
	@ManyToOne
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this._creator = creator;
	}

	@Override
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
		Brand other = (Brand) obj;
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
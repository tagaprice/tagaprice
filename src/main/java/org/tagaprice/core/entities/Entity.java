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
	private Long id = null;
	private String title;
	private Locale locale = null;
	private Date createdAt = null;
	private Integer revisionNumber = null;
	private Account creator = null;
	private Group group;

	protected Entity() { }

	protected Entity(Long id, String title, Locale locale, Date createdAt, int revisionNumber, Account creator, Group group) {
		this.id = id;
		this.title = title;
		this.locale = locale;
		this.createdAt = createdAt;
		this.revisionNumber = revisionNumber;
		this.creator = creator;
		this.group = group;
	}

	@Id
	@Column(name="ent_id")
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}
	@SuppressWarnings("unused")
	private void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne
	@JoinColumn(name = "locale_id")
	public Locale getLocale() {
		return locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return revisionNumber;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	@ManyToOne
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this.creator = creator;
	}

	@Transient
	public Group getGroup() {
		return group;
	}
	private void setGroup(Group group) {
		this.group = group;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((revisionNumber == null) ? 0 : revisionNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
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
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (revisionNumber == null) {
			if (other.revisionNumber != null)
				return false;
		} else if (!revisionNumber.equals(other.revisionNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Entity [_id=" + id + ", _currentRevisionNumber=" + revisionNumber + ", _createdAt=" + createdAt + ", _locale=" + locale + "]";
	}

}

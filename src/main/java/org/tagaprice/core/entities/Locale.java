package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "locale")
public class Locale {
	private int _id;
	private Locale _fallback = null;
	private String _title = null;
	private String _localTitle = null;
	private Date _createdAt = null;


	/**
	 * DONT USE THIS CONSTRUCTOR!
	 * loading doesn't work if this constructor is set private.
	 */
	protected Locale() { }

	public Locale(Locale fallback, String title, String localTitle, Date createdAt) {
		_fallback = fallback;
		_title = title;
		_localTitle = localTitle;
		_createdAt = createdAt;
	}


	@Id
	@Column(name = "locale_id")
	public int getId() {
		return _id;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		_id = id;
	}

	@ManyToOne
	@JoinColumn(name = "fallback_Id")
	public Locale getFallback() {
		return _fallback;
	}

	@SuppressWarnings("unused")
	private void setFallback(Locale fallback) {
		_fallback = fallback;
	}

	@Column(name = "title")
	public String getTitle() {
		return _title;
	}

	@SuppressWarnings("unused")
	private void setTitle(String title) {
		_title = title;
	}

	@Column(name = "localtitle")
	public String getLocalTitle() {
		return _localTitle;
	}

	@SuppressWarnings("unused")
	private void setLocalTitle(String localTitle) {
		_localTitle = localTitle;
	}

	@Column(name = "created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}

	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "Locale [_id=" + _id + ", _title=" + _title + ", _localTitle=" + _localTitle + ", _createdAt="
		+ _createdAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + _id;
		result = prime * result + ((_localTitle == null) ? 0 : _localTitle.hashCode());
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
		Locale other = (Locale) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_id != other._id)
			return false;
		if (_localTitle == null) {
			if (other._localTitle != null)
				return false;
		} else if (!_localTitle.equals(other._localTitle))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}

}

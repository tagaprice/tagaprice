package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "locale")
@SuppressWarnings("unused")
public class Locale {
	private Integer _id;
	private String _title = null;
	private String _localTitle = null;


	protected Locale() { }

	public Locale(Integer id, String title, String localTitle) {
		_id = id;
		_title = title;
		_localTitle = localTitle;
	}


	@Id
	@Column(name = "locale_id")
	public Integer getId() {
		return _id;
	}
	private void setId(Integer id) {
		_id = id;
	}

	@Column(name = "title")
	public String getTitle() {
		return _title;
	}
	private void setTitle(String title) {
		_title = title;
	}

	@Column(name = "localtitle")
	public String getLocalTitle() {
		return _localTitle;
	}
	private void setLocalTitle(String localTitle) {
		_localTitle = localTitle;
	}

	@Override
	public String toString() {
		return "Locale [_id=" + _id + ", _title=" + _title + ", _localTitle=" + _localTitle + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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

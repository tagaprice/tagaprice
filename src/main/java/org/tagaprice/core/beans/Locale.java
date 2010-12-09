package org.tagaprice.core.beans;

import java.util.Date;

/**
 * TODO regenerate equals and hashcode after implementatioin
 * @author haja
 *
 */
public class Locale {
	private int _id;
	private Locale _fallback = null;
	private String _title = null;
	private String _localTitle = null;
	private Date _createdAt = null;


	private Locale() { }


	public Locale(Locale fallback, String title, String localTitle, Date createdAt) {
		_fallback = fallback;
		_title = title;
		_localTitle = localTitle;
		_createdAt = createdAt;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param id the id to set
	 */
	private void setId(int id) {
		_id = id;
	}

	/**
	 * @return the fallback
	 */
	public Locale getFallback() {
		return _fallback;
	}

	/**
	 * @param fallback the fallback to set
	 */
	private void setFallback(Locale fallback) {
		_fallback = fallback;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		_title = title;
	}

	/**
	 * @return the localTitle
	 */
	public String getLocalTitle() {
		return _localTitle;
	}

	/**
	 * @param localTitle the localTitle to set
	 */
	public void setLocalTitle(String localTitle) {
		_localTitle = localTitle;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return _createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}


	@Override
	public String toString() {
		return "Locale [_id=" + _id + ", _title=" + _title + ", _localTitle="
		+ _localTitle + ", _createdAt=" + _createdAt + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _id;
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
		return true;
	}
}

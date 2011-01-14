package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="category")
@SuppressWarnings("unused")
public class Category {

	private Long _id;
	private Category _parent;
	private String _title;
	private Date _createdAt;
	private Account _creator;

	protected Category() { }

	public Category(Long id, String title, Category parent, Date createdAt, Account creator) {
		_id = id;
		_title = title;
		_parent = parent;
		_createdAt = createdAt;
		_creator = creator;
	}

	@Id
	@Column(name="category_id")
	public Long getId() {
		return _id;
	}
	private void setId(Long id) {
		_id = id;
	}

	public String getTitle() {
		return _title;
	}
	private void setTitle(String title) {
		_title = title;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id")
	public Category getParent() {
		return _parent;
	}
	private void setParent(Category parent) {
		_parent = parent;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}
	private void setCreator(Account creator) {
		_creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_creator == null) ? 0 : _creator.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((_parent == null) ? 0 : _parent.hashCode());
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
		Category other = (Category) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_creator == null) {
			if (other._creator != null)
				return false;
		} else if (!_creator.equals(other._creator))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_parent == null) {
			if (other._parent != null)
				return false;
		} else if (!_parent.equals(other._parent))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [_id=" + _id + ", _parent=" + _parent + ", _title=" + _title + ", _createdAt=" + _createdAt
		+ ", _creator=" + _creator + "]";
	}
}

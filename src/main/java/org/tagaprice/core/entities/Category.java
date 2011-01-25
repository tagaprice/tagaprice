package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

/**
 * <p>
 * This class represents a category, used to categorize for example {@link Product}s. But this class is not limited to
 * {@link Product}s only, in fact it can be used to categorize any entity.
 * </p>
 * 
 * <p>
 * Every {@link Category} object has a parent, which may be null. A {@link Category} is a node in a simple tree where
 * the root-node has null as its parent.
 * </p>
 * 
 * <p>
 * A {@link Category} has the following properties:
 * <ul>
 * <li>Id: primary identifier in the database</li>
 * <li>parent: parent category</li>
 * <li>title: short text to represent this category</li>
 * <li>createdAt: date this category was created</li>
 * <li>creator: {@link Account} who created this category</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This class is immutable. Properties once set, cannot be changed.
 * </p>
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "category")
@SuppressWarnings("unused")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long _id;
	private Category _parent;
	private String _title;
	private Date _createdAt;
	private Account _creator;

	/**
	 * this constructor is needed for hibernate.
	 */
	protected Category() {
	}

	/**
	 * Initialize a new {@link Category}.
	 * 
	 * @param id
	 *            Id of the category to create. Can be null, then this category is treated as new concerning the
	 *            database and a fresh id will be created and assigned. If id is not null it must be greater than 0.
	 * @param title
	 *            Short text to represent this category.
	 * @param parent
	 *            Parent category node. may be null to create a root node.
	 * @param createdAt
	 *            {@link Date} this category was created.
	 * @param creator
	 *            {@link Account} who created this category.
	 */
	public Category(Long id, String title, Category parent, Date createdAt, Account creator) {
		ArgumentUtitlity.checkNull("creator", creator);
		ArgumentUtitlity.checkNull("creator.id", creator.getUid());
		_id = id;
		_title = title;
		_parent = parent;
		_createdAt = createdAt;
		_creator = creator;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id")
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


	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DETACH } )
	@JoinColumn(name = "parent_id")
	public Category getParent() {
		return _parent;
	}

	private void setParent(Category parent) {
		_parent = parent;
	}


	@Column(name = "created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}

	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.DETACH } )
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}

	public void setCreator(Account creator) {
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

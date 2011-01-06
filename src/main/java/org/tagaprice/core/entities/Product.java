package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="product")
@SecondaryTables({
	@SecondaryTable(name="entity")
})
public class Product implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Long _id = null;
	private Locale _locale = null;
	private Set<ProductRevision> _revisions = new HashSet<ProductRevision>();
	private Date _createdAt;
	private Account _creator;

	public Product() { }

	public Product(Long id, Locale locale, Date createdAt, Account creator, Collection<? extends ProductRevision> revisions) {
		this._id = id;
		this._locale = locale;
		_revisions.addAll(revisions);
		this._createdAt = createdAt;
		this._creator = creator;
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

	//	@ManyToOne
	//	@JoinColumn(table="entity", name = "locale_id", referencedColumnName="locale_id")
	@Transient
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@Column(table="entity",name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	//	@ManyToOne
	//	@JoinColumn(table="entity", name = "creator")
	@Transient
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this._creator = creator;
	}

	//	@OneToMany
	//	@JoinTable(
	//			name="Revisions",
	//			joinColumns = @JoinColumn( name="ent_id"),
	//			inverseJoinColumns = @JoinColumn( name="ent_id")
	//	)
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="ent_id")
	public Set<ProductRevision> getRevisions() {
		return _revisions;
	}
	@SuppressWarnings("unused")
	private void setRevisions(Set<ProductRevision> revisions) {
		_revisions = revisions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
		Product other = (Product) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [_id=" + _id + "]";
	}


}

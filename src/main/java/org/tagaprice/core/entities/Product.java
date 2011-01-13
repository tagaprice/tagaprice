package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "product")
@SecondaryTables({ @SecondaryTable(name = "entity") })
@SuppressWarnings("unused")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long _id = null;
	private Set<ProductRevision> _revisions = new HashSet<ProductRevision>();
	private Locale _locale;

	public Product() {
	}

	public Product(Long id, Locale locale, Collection<? extends ProductRevision> revisions) {
		_id = id;
		_locale = locale;
		_revisions.addAll(revisions);
	}

	@Id
	@Column(name = "ent_id")
	public Long getId() {
		return _id;
	}
	private void setId(Long id) {
		this._id = id;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(table = "entity", name = "locale_id", referencedColumnName = "locale_id")
	public Locale getLocale() {
		return _locale;
	}
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ent_id")
	public Set<ProductRevision> getRevisions() {
		return _revisions;
	}
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

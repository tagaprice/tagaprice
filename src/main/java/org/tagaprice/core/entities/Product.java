package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.*;

import org.hamcrest.Matcher;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * <p>
 * This class represents a product. Most of the properties of a product are represented by a set of
 * {@link ProductRevision}s. Every such {@link ProductRevision} represents a version of this product.
 * </p>
 * 
 * <p>
 * A {@link Product} has the following properties:
 * <ul>
 * <li>Id: primary identifier in the database</li>
 * <li>locale: {@link Locale} which indicates language and location of this product</li>
 * <li>revisions: a set of {@link ProductRevision}s, each representing one version of this product</li>
 * </ul>
 * </p>
 * 
 * <p>
 * TODO This class may should be immutable.
 * </p>
 * 
 * @author haja
 * @author forste
 * 
 */
@Entity
@Table(name = "product")
@SecondaryTables({ @SecondaryTable(name = "entity") })
@SuppressWarnings("unused")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long _id = null;
	private SortedSet<ProductRevision> _revisions;
	private Locale _locale;
	private static final Comparator<? super ProductRevision> _revisionComparator = new RevisionComparator();

	/**
	 * this constructor is need for hibernate.
	 */
	protected Product() {
	}

	/**
	 * Initialize a new {@link Product}.
	 * 
	 * @param id
	 *            Id of Product to create. Can be null, in which case this product and all its revisions are treated as
	 *            new concerning the database and a fresh id will be created and assigned. If id is not null it must be
	 *            greater than 0.
	 * @param locale
	 *            indicates the language and location of this product.
	 * @param revisions
	 *            A non-empty set of ProductRevisions. The Set must also have consecutive revisions numbers without
	 *            gaps. E.g. the set with revisions: 2,3,4 is valid whereas the set with revisions: 2,4,5 is invalid.
	 */
	public Product(Long id, Locale locale, Set<ProductRevision> revisions) {
		_id = id;
		_locale = locale;
		_revisions.addAll(revisions);
	}

	@Id
	@Column(name = "ent_id")
	public Long getId() {
		return _id;
	}

	/**
	 * TODO this is public due to service having to set the id if not present, should not be public probably
	 * This violates immutability of this class.
	 */
	public void setId(Long id) {
		this._id = id;
		for (ProductRevision rev : _revisions) {
			rev.setId(id);
		}
	}


	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(table = "entity", name = "locale_id", referencedColumnName = "locale_id")
	public Locale getLocale() {
		return _locale;
	}

	private void setLocale(Locale locale) {
		this._locale = locale;
	}


	/**
	 * TODO this allows changing the {@link ProductRevision}s of this product. this violates immutability of this class.
	 * Allthoug, this might be desireable...
	 * 
	 * TODO this should be a sortedset to verify that revisions are always ordered
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "ent_id")
	@Sort(type = SortType.COMPARATOR, comparator = RevisionComparator.class)
	public SortedSet<ProductRevision> getRevisions() {
		return _revisions;
	}

	private void setRevisions(SortedSet<ProductRevision> revisions) {
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


	/**
	 * Returns the current, i.e. highest, revision of this product.
	 * <p>TODO this allows changing the {@link ProductRevision} of this product. this violates immutability of this class.</p>
	 */
	@Transient
	public ProductRevision getCurrentRevision() {
		List<ProductRevision> list = new ArrayList<ProductRevision>(_revisions);
		Collections.sort(list, Product._revisionComparator);
		return list.iterator().next();
	}
}

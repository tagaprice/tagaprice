package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
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
 * <li>revisions: a {@link SortedSet} of , each representing one version of this product. Sorted by revision number, highest first.</li>
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
	private SortedSet<ProductRevision> _revisions = new TreeSet<ProductRevision>(new RevisionComparator());
	private Locale _locale;

	/**
	 * this constructor is need for hibernate.
	 */
	protected Product() {
	}

	/**
	 * Initialize a new {@link Product}.
	 * 
	 * @param id
	 *            Identifier of Product to create. Can be null, in which case this product and all its revisions are treated as
	 *            new concerning the database and a fresh id will be created and assigned. If id is not null it must be
	 *            greater than 0.
	 * @param locale
	 *            indicates the language and location of this product, must not be null.
	 * @param revisions
	 *            A non-empty set of {@link ProductRevision}s. The {@link Set} must have {@link ProductRevision}s with consecutive revisions numbers without
	 *            gaps. E.g. the set with revisions: 2,3,4 is valid whereas the set with revisions: 2,4,5 is invalid.
	 */
	public Product(Long id, Locale locale, Set<ProductRevision> revisions) {
		ArgumentUtitlity.checkNull("locale", locale);
		ArgumentUtitlity.checkNull("revisions", revisions);
		if(revisions.isEmpty())
			throw new IllegalArgumentException("revisions must not be empty");
		if(id != null && id <= 0L)
			throw new IllegalArgumentException("id must not be greater than 0 or null");

		_id = id;
		_locale = locale;
		_revisions.addAll(revisions);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ent_id")
	public Long getId() {
		return _id;
	}

	/**
	 * This sets the id of this product. It also updates the id of each {@link ProductRevision} to match the new id.
	 * 
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
	 * Returns a copy of all {@link ProductRevision}s of this product as a {@link SortedSet} sorted by revision number, highest revision first.
	 */
	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE } )
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
	 * Returns the current, i.e. with the highest revision number, revision of this product.
	 */
	@Transient
	public ProductRevision getCurrentRevision() {
		return _revisions.first();
	}

	/**
	 * Adds a new revision to this product. 
	 */
	public void addRevision(ProductRevision newRevision) {
		_revisions.add(newRevision);
		
	}
}

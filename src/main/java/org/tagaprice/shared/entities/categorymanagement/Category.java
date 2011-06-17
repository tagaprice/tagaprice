package org.tagaprice.shared.entities.categorymanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.accountmanagement.User;

/**
 * A category describes which properties and dependencies a {@link Product} has.
 *
 */
public class Category extends AEntity   {
	private static final long serialVersionUID = 1L;

	private Category _parentCategory;


	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Category(){}

	/**
	 * <b>NEW</b>
	 * Create a new Category
	 * @param creator Creator of the current entity revision
	 * @param title the title of the category
	 * @param parent the parent of the category.
	 */
	public Category(User creator, String title, Category parent) {
		this(creator, null, null, title, parent);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Update or fetch an category
	 * @param creator Creator of the current entity revision
	 * @param revisionId the revision and id of the category
	 * @param title the title of the category
	 * @param parent the parent of the category
	 */
	public Category(User creator, String id, String revision, String title, Category parent){
		super(creator, id, revision, title);
		setParentCategory(parent);
	}

	/**
	 * Returns the parent {@link Category}.
	 * @return parent {@link Category}
	 */
	@JSONProperty(value="parent")
	public Category getParentCategory() {
		return this._parentCategory;
	}

	/**
	 * Sets the parent {@link Category}.
	 * @param category set parent {@link Category}
	 */
	public void setParentCategory(Category category) {
		this._parentCategory = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category: "+getId()+" [_parentCategory=" + _parentCategory + "]";
	}


}

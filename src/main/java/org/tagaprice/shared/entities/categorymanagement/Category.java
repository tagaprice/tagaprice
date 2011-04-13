package org.tagaprice.shared.entities.categorymanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.*;

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
	 * @param title the title of the category
	 * @param parent the parent of the category.
	 */
	public Category(String title, Category parent) {
		this(null, null, title, parent);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Update or fetch an category
	 * @param revisionId the revision and id of the category
	 * @param title the title of the category
	 * @param parent the parent of the category
	 */
	public Category(String id, String revision, String title, Category parent){
		super(id, revision, title);
		setParentCategory(parent);
	}

	@JSONProperty(value="parent")
	public Category getParentCategory() {
		return this._parentCategory;
	}

	public void setParentCategory(Category category) {
		this._parentCategory = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString()+" Category [_parentCategory=" + _parentCategory + "]";
	}

}

package org.tagaprice.shared.entities.dump;

import org.tagaprice.shared.entities.*;

/**
 * A category describes which properties and dependencies a {@link IProduct} has.
 *
 */
public class Category extends AEntity implements ICategory  {
	private static final long serialVersionUID = 1L;

	private ICategory _parentCategory;


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
	public Category(String title, ICategory parent) {
		super(title);
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Update or fetch an category
	 * @param revisionId the revision and id of the category
	 * @param title the title of the category
	 * @param parent the parent of the category
	 */
	public Category(IRevisionId revisionId, String title, ICategory parent){
		super(revisionId, title);
	}

	@Override
	public ICategory getParentCategory() {
		return this._parentCategory;
	}


	@Override
	public void setParentCategory(ICategory category) {
		this._parentCategory = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [_parentCategory=" + _parentCategory + "]";
	}


}

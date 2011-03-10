package org.tagaprice.client.gwt.shared.entities.dump;

import org.tagaprice.client.gwt.shared.entities.*;


public class Category extends AEntity<ICategory> implements ICategory  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814196347951588949L;
	private ICategory _parentCategory;
	private long _id;

	public Category(){}

	public Category(String title) {
		super(title);
	}

	@Override
	public ICategory getParentCategory() {
		return this._parentCategory;
	}


	@Override
	public String toString() {
		String text = this.getTitle();
		ICategory parent = this._parentCategory;
		while(parent != null) {
			text = parent.getTitle() + "->" + text;
			parent = parent.getParentCategory();
		}
		return text;
	}

	@Override
	public void setParentCategory(ICategory category) {
		this._parentCategory = category;
	}



	@Override
	public void setId(long id) {
		this._id = id;
	}

	@Override
	public long getId() {
		return this._id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (_id ^ (_id >>> 32));
		result = prime * result + ((_parentCategory == null) ? 0 : _parentCategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (_id != other._id)
			return false;
		if (_parentCategory == null) {
			if (other._parentCategory != null)
				return false;
		} else if (!_parentCategory.equals(other._parentCategory))
			return false;
		return true;
	}


}

package org.tagaprice.client.gwt.shared.entities.dump;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;


public class Category extends AEntity<ICategory> implements ICategory  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814196347951588949L;
	private ICategory _parentCategory;
	private ArrayList<ICategory> _childCategories = new ArrayList<ICategory>();
	private int _id;

	public Category(){}

	public Category(String title) {
		super(title);
	}

	@Override
	public ICategory getParentCategory() {
		return this._parentCategory;
	}

	@Override
	public ArrayList<ICategory> getChildCategories() {
		return this._childCategories;
	}

	@Override
	public void addChildCategory(ICategory category) {
		this._childCategories.add(category);
		category.setParentCategory(this);
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
	public ICategory copy() {
		return this;
	}

	@Override
	public boolean equals(Object o) {
		//TODO Implement this correct
		return (o.toString().equals(this.toString()));
	}

	@Override
	public void setId(int id) {
		this._id = id;
	}

	@Override
	public int getId() {
		return this._id;
	}

}

package org.tagaprice.client.gwt.shared.entities.dump;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;


public class Category extends AEntity implements ICategory  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814196347951588949L;
	private ICategory parentCategory;
	private ArrayList<ICategory> childCategories = new ArrayList<ICategory>();

	public Category(){}

	public Category(String title) {
		super(title);
	}

	@Override
	public ICategory getParentCategory() {
		return this.parentCategory;
	}

	@Override
	public ArrayList<ICategory> getChildCategories() {
		return this.childCategories;
	}

	@Override
	public void addChildCategory(ICategory category) {
		this.childCategories.add(category);
		category.setParentCategory(this);
	}

	@Override
	public String toString() {
		String text = this.getTitle();
		ICategory parent = this.parentCategory;
		while(parent != null) {
			text = parent.getTitle() + "->" + text;
			parent = parent.getParentCategory();
		}
		return text;
	}

	@Override
	public void setParentCategory(ICategory category) {
		this.parentCategory = category;
	}


}

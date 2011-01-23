package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Locale;

public class CategoryConverter {
	/**
	 * the singleton
	 */
	private static CategoryConverter instance = new CategoryConverter();
	/*
	 * default Values
	 */
	public static Locale defaultLocale = new Locale(1, "de", "de");
	public static Account defaultAccount = new Account(1L, "love@you.org", "super", new Date());
	public static Category defaultCategory = new Category(1L, "X", null, new Date(), CategoryConverter.defaultAccount);

	/*
	 * Caching
	 */
	private HashMap<Long, Category> coreCategories = new HashMap<Long, Category>();
	private HashMap<Long, ICategory> gwtCategories = new HashMap<Long, ICategory>();

	public static CategoryConverter getInstance() {
		return CategoryConverter.instance;
	}

	public ICategory convertCoreCategoryToGWT(Category coreCategory) {
		ICategory gwtCategory = new org.tagaprice.client.gwt.shared.entities.dump.Category();
		gwtCategory.setId(coreCategory.getId());
		gwtCategory.setTitle(coreCategory.getTitle());
		ICategory parentGWTCategory;
		if(coreCategory.getParent() != null) {
			parentGWTCategory = this.gwtCategories.get(coreCategory.getParent().getId());
			if(parentGWTCategory != null) {
				gwtCategory.setParentCategory(parentGWTCategory);
			} else {
				gwtCategory.setParentCategory(this.convertCoreCategoryToGWT(coreCategory.getParent()));
			}
		} else {
			//Nothing, is local root
		}
		return gwtCategory;
	}

	public Category convertGWTCategoryToCore(ICategory gwtCategory) {
		return null;
	}

}

package org.tagaprice.client.gwt.server.diplomat.converter;

import java.util.*;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.core.entities.*;

public class CategoryConverter {
	/**
	 * the singleton
	 */
	private static CategoryConverter instance = new CategoryConverter();
	/*
	 * Caching
	 */
	private HashMap<Long, Category> coreCategories = new HashMap<Long, Category>();
	private HashMap<Long, ICategory> gwtCategories = new HashMap<Long, ICategory>();

	public static CategoryConverter getInstance() {
		return CategoryConverter.instance;
	}
	public ICategory convertCoreCategoryToGWT(Category coreCategory) {
		return this.convertCoreCategoryToGWT(coreCategory, true);
	}

	private ICategory convertCoreCategoryToGWT(Category coreCategory, boolean reset) {
		if(coreCategory == null) {
			return null;
		}
		if(reset)
			this.gwtCategories.clear();
		ICategory gwtCategory = this.gwtCategories.get(coreCategory.getId());
		if(gwtCategory != null) {
			return gwtCategory;
		}
		/*
		 * The gwtCategory is not cached now...
		 */
		gwtCategory = new org.tagaprice.client.gwt.shared.entities.dump.Category();
		gwtCategory.setId(coreCategory.getId());
		gwtCategory.setTitle(coreCategory.getTitle());
		ICategory parentGWTCategory;
		Category parentCoreCategory;
		if((parentCoreCategory = coreCategory.getParent()) != null) {
			parentGWTCategory = this.gwtCategories.get(parentCoreCategory.getId());
			if(parentGWTCategory == null) {

				/*
				 * Parent Category is not converted now.
				 * Convert it recursivley.
				 */
				parentGWTCategory = this.convertCoreCategoryToGWT(parentCoreCategory, false);
			}
			gwtCategory.setParentCategory(parentGWTCategory);
		} else {
			//Nothing, is local root
		}
		this.gwtCategories.put(coreCategory.getId(), gwtCategory);
		return gwtCategory;
	}

	public Category convertGWTCategoryToCore(ICategory gwtCategory) {
		return this.convertGWTCategoryToCore(gwtCategory, true);
	}

	private Category convertGWTCategoryToCore(ICategory gwtCategory, boolean reset) {
		if(gwtCategory == null) {
			return null;
		}
		if(reset) {
			this.coreCategories.clear();
		}
		Category coreCategory = this.coreCategories.get(gwtCategory.getId());
		if(coreCategory != null) {
			return coreCategory;
		}
		/*
		 * The coreCategory is not cached now...
		 */
		long id = gwtCategory.getId();
		String title = gwtCategory.getTitle();
		Category parentCoreCategory = this.convertGWTCategoryToCore(gwtCategory.getParentCategory(), false);

		coreCategory = new Category(id, title, parentCoreCategory, DefaultValues.defaultDate, DefaultValues.defaultCoreAccount);
		this.coreCategories.put(id, coreCategory);

		return coreCategory;
	}



}

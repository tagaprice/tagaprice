package org.tagaprice.client.generics.widgets.devView;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.generics.widgets.ICategorySelecter;
import org.tagaprice.shared.entities.dump.Category;
import org.tagaprice.shared.logging.*;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * The class will manage the Category selection, and will also communicate with the server.
 * 
 */
public class CategorySelecter extends Composite implements ICategorySelecter {
	private MyLogger logger = LoggerFactory.getLogger(CategorySelecter.class);

	private ListBox _listBoxCategories = new ListBox();
	private List<Category> _availableCategories = new ArrayList<Category>();

	public CategorySelecter() {
		initWidget(_listBoxCategories);
	}

	@Override
	public void setCategory(Category category) {
		logger.log("set category " + category);
		if (category != null) {
			for (int i = 0; i < this._listBoxCategories.getItemCount(); i++) {
				if(this._listBoxCategories.getValue(i).equals(category.toString())){
					logger.log("Category found");
					this._listBoxCategories.setSelectedIndex(i);
					return;
				}

			}
		}
	}

	@Override
	public Category getCategory() {
		if(this._availableCategories != null && this._availableCategories.size() > 0) {
			return this._availableCategories.get(this._listBoxCategories.getSelectedIndex());
		} else {
			return null;
		}
	}

	@Override
	public void setAvailableCategories(List<Category> categories) {
		this._availableCategories = categories;
		this._listBoxCategories.clear();
		for (Category c : this._availableCategories) {
			this._listBoxCategories.addItem(c.getTitle(), c.toString());
		}
	}
}

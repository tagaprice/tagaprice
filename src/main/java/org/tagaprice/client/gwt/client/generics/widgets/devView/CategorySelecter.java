package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.ICategorySelecter;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * The class will manage the Category selection, and will also communicate with the server.
 * 
 */
public class CategorySelecter extends Composite implements ICategorySelecter {
	MyLogger logger = LoggerFactory.getLogger(CategorySelecter.class);

	ListBox _listBoxCategories = new ListBox();
	ArrayList<ICategory> _availableCategories;

	public CategorySelecter() {
		initWidget(_listBoxCategories);
	}

	@Override
	public void setCategory(ICategory category) {
		logger.log("set category " + category);
		if (category != null) {
			for (int i = 0; i < this._listBoxCategories.getItemCount(); i++) {
				logger.log("investigate " + this._listBoxCategories.getValue(i));
				if (this._listBoxCategories.getItemText(i).equals(category.toString())) {
					this._listBoxCategories.setSelectedIndex(i);
					return;
				}
			}
		}
	}

	@Override
	public ICategory getCategory() {
		return this._availableCategories.get(this._listBoxCategories.getSelectedIndex());
	}

	@Override
	public void setAvailableCategories(ArrayList<ICategory> categories) {
		this._availableCategories = categories;
		this._listBoxCategories.clear();
		for (ICategory c : this._availableCategories) {
			this._listBoxCategories.addItem(c.toString(), c.getTitle());
		}
	}
}

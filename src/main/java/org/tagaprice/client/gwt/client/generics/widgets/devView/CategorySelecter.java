package org.tagaprice.client.gwt.client.generics.widgets.devView;

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

	ListBox categories = new ListBox();

	public CategorySelecter() {
		initWidget(categories);
		categories.addItem("root->food");
		categories.addItem("root->beverages");
		categories.addItem("root->beverages->nonalcoholics");
		categories.addItem("root->beverages->alcoholics");
	}

	@Override
	public void setCategory(ICategory category){
		logger.log("set category " + category.toString());
		for(int i = 0; i < this.categories.getItemCount(); i++) {
			logger.log("investigate " + this.categories.getItemText(i));
			if(this.categories.getItemText(i).equals(category.toString())) {
				this.categories.setSelectedIndex(i);
				return;
			}
		}
	}
}

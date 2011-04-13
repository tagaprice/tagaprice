package org.tagaprice.client.generics.widgets.devView;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.generics.widgets.ICategorySelecter;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.logging.*;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * The class will manage the Category selection, and will also communicate with the server.
 * 
 */
public class CategorySelecter extends Composite implements ICategorySelecter {
	private MyLogger logger = LoggerFactory.getLogger(CategorySelecter.class);

	private SimplePanel _siPa = new SimplePanel();
	private ListBox _listBoxCategories = new ListBox();
	private List<Category> _availableCategories = new ArrayList<Category>();
	private ICategoryServiceAsync _categoryServiceAsync = GWT.create(ICategoryService.class);


	public CategorySelecter() {
		initWidget(_siPa);

		Category root = new Category("root", null);
		Category one = new Category("one",root);
		Category two = new Category("two", one);
		setCategory(two);

		_siPa.setWidget(new SimpleCategorySelecter(null));
	}

	@Override
	public void setCategory(Category category) {
		logger.log("set category " + category);
		if (category != null) {
			_siPa.setWidget(new SimpleCategorySelecter(category));

			/*
			for (int i = 0; i < this._listBoxCategories.getItemCount(); i++) {
				if(this._listBoxCategories.getValue(i).equals(category.toString())){
					logger.log("Category found");
					this._listBoxCategories.setSelectedIndex(i);
					return;
				}

			}
			 */
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
		/*
		this._availableCategories = categories;
		this._listBoxCategories.clear();
		for (Category c : this._availableCategories) {
			this._listBoxCategories.addItem(c.getTitle(), c.toString());
		}
		 */
	}

	class SimpleCategorySelecter extends Composite{

		private ListBox _catList = new ListBox();

		private HorizontalPanel hoPa1 = new HorizontalPanel();
		Category _myCat = null;

		public SimpleCategorySelecter(Category category) {
			_myCat=category;
			logger.log("CreateSimpleCategory " + category);
			initWidget(hoPa1);

			//Empty root element



			if(_myCat!=null && _myCat.getParentCategory()!=null){
				hoPa1.add(new SimpleCategorySelecter(_myCat.getParentCategory()));
			}

			if(_myCat!=null)
				_catList.addItem(_myCat.getTitle(),_myCat.getId());

			hoPa1.add(_catList);



			_catList.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					//Implement listener
					String id=null;
					if(_myCat!=null)
						id=_myCat.getId();
					_categoryServiceAsync.getCategoryChilds(id, new AsyncCallback<List<Category>>() {

						@Override
						public void onSuccess(List<Category> result) {
							_catList.clear();
							for(Category c: result)
								_catList.addItem(c.getTitle(),c.getId());
						}

						@Override
						public void onFailure(Throwable e) {
							logger.log("get categories problem" + e);
						}
					});
				}
			});
		}
	}
}

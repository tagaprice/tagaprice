package org.tagaprice.client.generics.widgets.devView;

import java.util.List;

import org.tagaprice.client.generics.widgets.ICategorySelecter;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The class will manage the Category selection, and will also communicate with the server.
 * 
 */
public class CategorySelecter extends Composite implements ICategorySelecter {


	private ICategoryServiceAsync _categoryServiceAsync = GWT.create(ICategoryService.class);
	private HorizontalPanel _hoPa = new HorizontalPanel();


	public CategorySelecter() {
		initWidget(_hoPa);


		_hoPa.add(new SimpleCategorySelecter(null));
	}

	@Override
	public void setCategory(Category category) {
		Log.debug("set category " + category);
		_hoPa.clear();
		if (category != null) {

			Category newCat = category;

			while(newCat!=null){
				_hoPa.insert(new SimpleCategorySelecter(newCat), 0);
				newCat = newCat.getParentCategory();
			}


		}
		_hoPa.insert(new SimpleCategorySelecter(null), 0);
	}

	@Override
	public Category getCategory() {
		if(_hoPa.getWidgetCount()>0)
			return ((SimpleCategorySelecter)_hoPa.getWidget(_hoPa.getWidgetCount()-1)).getCategory();

		return null;
	}


	class SimpleCategorySelecter extends Composite{


		private HorizontalPanel hoPa1 = new HorizontalPanel();
		private Button arrow = new Button("->");
		private Label text = new Label("");
		Category _myCat = null;
		private PopupPanel showCats = new PopupPanel(true);

		public SimpleCategorySelecter(Category category) {
			_myCat=category;
			Log.debug("CreateSimpleCategory " + category);



			if(_myCat!=null){
				text.setText(_myCat.getTitle());
			}

			hoPa1.add(text);
			hoPa1.add(arrow);
			initWidget(hoPa1);


			arrow.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					showCats.setWidget(new Label("loading..."));
					showCats.showRelativeTo(arrow);

					String id=null;




					if(_myCat!=null)
						id=_myCat.getId();

					Log.debug("getChildsFor: "+id+", _myCat: "+_myCat);
					_categoryServiceAsync.getCategoryChildren(id, new AsyncCallback<List<Category>>() {


						@Override
						public void onSuccess(List<Category> results) {
							VerticalPanel vePa = new VerticalPanel();

							for(final Category c: results){
								Label catText = new Label(c.getTitle());
								vePa.add(catText);

								catText.addClickHandler(new ClickHandler() {

									@Override
									public void onClick(ClickEvent arg0) {
										setCategory(c);

									}
								});
							}
							showCats.setWidget(vePa);
							showCats.showRelativeTo(arrow);
						}

						@Override
						public void onFailure(Throwable e) {
							Log.error("getCategoryProblem: "+e);
						}
					});

				}
			});

		}

		public Category getCategory(){
			return _myCat;
		}
	}
}

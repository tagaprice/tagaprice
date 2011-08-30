package org.tagaprice.client.generics.widgets.desktopView;

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
import com.google.gwt.user.client.ui.Image;
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
	private boolean _readonly = true;
	private boolean _categoryTypeIsProduct = true;


	public CategorySelecter() {
		_hoPa.setStyleName("categorySelecter");
		initWidget(_hoPa);

		SimpleCategorySelecter c = new SimpleCategorySelecter(null);
		c.setReadOnly(_readonly);
		_hoPa.add(c);
	}

	@Override
	public void setCategory(Category category) {
		Log.debug("set category " + category);
		_hoPa.clear();
		if (category != null) {

			Category newCat = category;

			while(newCat!=null){
				SimpleCategorySelecter c = new SimpleCategorySelecter(newCat);
				c.setReadOnly(_readonly);
				_hoPa.insert(c, 0);
				newCat = newCat.getParent();
			}


		}
		SimpleCategorySelecter c = new SimpleCategorySelecter(null);
		c.setReadOnly(_readonly);
		_hoPa.insert(c, 0);
	}

	@Override
	public Category getCategory() {
		if(_hoPa.getWidgetCount()>0)
			return ((SimpleCategorySelecter)_hoPa.getWidget(_hoPa.getWidgetCount()-1)).getCategory();

		return null;
	}
	
	@Override
	public boolean isReadOnly() {
		return _readonly;
	}
	
	@Override
	public void setReadOnly(boolean read) {
		if(read){
			_readonly=true;
		}else{
			_readonly=false;
		}
		
		for(int i=0;i<_hoPa.getWidgetCount();i++){
			((SimpleCategorySelecter)_hoPa.getWidget(i)).setReadOnly(_readonly);
		}
	}


	class SimpleCategorySelecter extends Composite{


		private HorizontalPanel hoPa1 = new HorizontalPanel();
		private Image arrow = new Image("desktopView/arrowBlackRight.png");
		private Label text = new Label("");
		private Category _myCat = null;
		private PopupPanel showCats = new PopupPanel(true);
		private boolean _readonly = true;

		public SimpleCategorySelecter(Category category) {
			_myCat=category;
			Log.debug("CreateSimpleCategory " + category);



			if(_myCat!=null){
				text.setText(_myCat.getTitle());
			}

			arrow.setStyleName("arrow");
			text.setStyleName("text");
			showCats.setStyleName("popBackground");
			showCats.getElement().getStyle().setZIndex(2000);
			
			hoPa1.add(text);
			hoPa1.add(arrow);
			initWidget(hoPa1);


			arrow.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					
					if(!_readonly){
					
						showCats.setWidget(new Label("loading..."));
						showCats.showRelativeTo(arrow);
	
						String id=null;
	
						if(_myCat!=null)
							id=_myCat.getId();
	
						Log.debug("getChildsFor: "+id+", _myCat: "+_myCat);
						
						
						if(_categoryTypeIsProduct){
						
						
						_categoryServiceAsync.getProductCategoryChildren(id, new AsyncCallback<List<Category>>() {
	
	
							@Override
							public void onSuccess(List<Category> results) {
								drawCategories(results);
							}
	
							@Override
							public void onFailure(Throwable e) {
								Log.error("getCategoryProblem: "+e);
							}
						});
						}else if(!_categoryTypeIsProduct){
							_categoryServiceAsync.getShopCategoryChildren(id, new AsyncCallback<List<Category>>() {
								
								@Override
								public void onSuccess(List<Category> results) {
									drawCategories(results);									
								}
								
								@Override
								public void onFailure(Throwable e) {
									Log.error("getCategoryProblem: "+e);
								}
							});
						}
	
					}
				}
			});

		}

		public Category getCategory(){
			return _myCat;
		}
		
		public void setReadOnly(boolean read){
			if(read){
				_readonly=true;
				arrow.setStyleName("arrow");
			}else{
				_readonly=false;
				arrow.setStyleName("arrow edit");
			}
		}
		
		private void drawCategories(List<Category> results){
			VerticalPanel vePa = new VerticalPanel();
			
			for(final Category c: results){
				Label catText = new Label(c.getTitle());
				vePa.add(catText);

				catText.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent arg0) {
						setCategory(c);
						showCats.hide();
					}
				});
			}
			showCats.setWidget(vePa);
			showCats.showRelativeTo(arrow);
		}
		
	}


	@Override
	public void setCategoryTypeIsProduct(boolean isProduct) {
		_categoryTypeIsProduct=isProduct;		
	}
	
	
}

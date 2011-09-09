package org.tagaprice.client.features.categorymanagement.shop.desktopView;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.generics.events.CategorySelectedEventHandler;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.Composite;

public class ShopCategoryView extends Composite implements ICategoryView {

	
	private StdFrame _stdFrame = new StdFrame();
	private CategorySelecter _category = new CategorySelecter();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private Presenter _presenter;
	
	public ShopCategoryView() {
		_category.setCategoryTypeIsProduct(false);
		
		//head
		_stdFrame.setHeader(_category);
		
		//body
		_stdFrame.setBody(_statistic);
		
		_category.addCategorySelectedEventHandler(new CategorySelectedEventHandler() {
			
			@Override
			public void onCategoryClicked(String categoryId) {
				_presenter.onCategoryClicked(categoryId);
			}
		});
		
		initWidget(_stdFrame);
	}
	

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;			
	}

	@Override
	public void setCategory(Category category) {
		_category.setCategory(category);
	}


	@Override
	public Category getCategory() {
		return _category.getCategory();
	}


	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		_statistic.setStatisticResults(results);		
	}


	@Override
	public void setStatisticLatLon(double lat, double lon) {
		_statistic.setLatLon(lat, lon);		
	}


	@Override
	public void setReadOnly(boolean read) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public BoundingBox getStatisticBoundingBox() {
		return _statistic.getBoundingBox();
	}


	@Override
	public Date getStatisticBeginDate() {
		return _statistic.getBeginDate();
	}


	@Override
	public Date getStatisticEndDate() {
		return _statistic.getEndDate();
	}
}

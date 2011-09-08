package org.tagaprice.client.features.categorymanagement.product.desktopView;

import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;

import com.google.gwt.user.client.ui.Composite;

public class ProductCategoryView extends Composite implements ICategoryView {

	
	private StdFrame _stdFrame = new StdFrame();
	private CategorySelecter _category = new CategorySelecter();
	private StatisticSelecter _statistic = new StatisticSelecter();
	
	public ProductCategoryView() {
		_category.setCategoryTypeIsProduct(true);
		
		//head
		_stdFrame.setHeader(_category);
		
		//body
		_stdFrame.setBody(_statistic);
		
		initWidget(_stdFrame);
	}
	

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}

}

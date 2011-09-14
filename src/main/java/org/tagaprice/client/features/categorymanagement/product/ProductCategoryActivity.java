package org.tagaprice.client.features.categorymanagement.product;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.categorymanagement.ICategoryView;
import org.tagaprice.client.features.categorymanagement.ICategoryView.Presenter;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ProductCategoryActivity extends AbstractActivity implements Presenter {

	
	private ClientFactory _clientFactory;
	private ProductCategoryPlace _place;
	private ICategoryView _categoryView;
	private Category _category;
	private int _statisticDebounce = 0;
	
	public ProductCategoryActivity(ProductCategoryPlace place, ClientFactory clientFactory) {
		_place=place;
		_clientFactory=clientFactory;
	}
	
	
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		Log.debug("Activity starts...");
		_categoryView=_clientFactory.getProductCategoryView();
		_categoryView.setPresenter(this);		
		
		
		if(_place.getId()!=null){	
			_clientFactory.getCategoryService().getProductCategory(_place.getId(), new AsyncCallback<Category>() {
				
				@Override
				public void onSuccess(Category response) {
					Window.setTitle("Product Category - "+response.getTitle());
					
					updateView(response);
					panel.setWidget(_categoryView);
					
					//setLatLng
					if(_place.getLat()!=null && _place.getLon()!=null)
						_categoryView.setStatisticLatLon(
										Double.parseDouble(_place.getLat()), 
										Double.parseDouble(_place.getLon()));
				}
				
				@Override
				public void onFailure(Throwable caught) {
					try{
						throw caught;
					}catch (DaoException e){
						Log.error("DaoException at getProduct: "+caught.getMessage());
					} catch (Throwable e) {
						Log.error("Unexpected exception: "+caught.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(ProductCategoryActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
				
					}
					
				}
			});
			
		}
	}

	
	private void updateView(Category category){
		_category=category;
		_categoryView.setCategory(category);
		
	}
	
	
	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);		
	}


	@Override
	public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end) {
		Log.debug("onStatisticChangedEvent: bbox: "+bbox+", begin: "+begin+", end: "+end);

		_statisticDebounce++;
		final int curDebounce=_statisticDebounce;
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(ProductCategoryActivity.class));
		
		final InfoBoxShowEvent loadingInfo = new InfoBoxShowEvent(ProductCategoryActivity.class, "Getting statistic data... ", INFOTYPE.INFO,0);
		_clientFactory.getEventBus().fireEvent(loadingInfo);
		
		_clientFactory.getSearchService().searchProductCategoryPrices(_category.getId(), bbox, begin, end, new AsyncCallback<List<StatisticResult>>() {
			
			@Override
			public void onSuccess(List<StatisticResult> response) {
				if(curDebounce==_statisticDebounce){
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(loadingInfo));
					_categoryView.setStatisticResults(response);
					
				}
				
			}
			
			@Override
			public void onFailure(Throwable e) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(ProductCategoryActivity.class, INFOTYPE.INFO));
				Log.error("searchproblem: "+e);
			}
		});

	}


	@Override
	public void onCategoryClicked(String categoryId) {
		goTo(new ProductCategoryPlace(
				categoryId, 
				null, 
				_place.getLat(), 
				_place.getLon(), 
				_place.getZoom()));
		
	}

}

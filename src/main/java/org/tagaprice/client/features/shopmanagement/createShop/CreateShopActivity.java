package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.*;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {

	private Shop _shop;
	private ICreateShopView _createShopView;
	private CreateShopPlace _place;
	private ClientFactory _clientFactory;
	private int _statisticDebounce = 0;
	
	public CreateShopActivity(CreateShopPlace place, ClientFactory clientFactory) {
		Log.debug("create class");
		_place = place;
		_clientFactory = clientFactory;
	}


	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public String mayStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent() {
		Log.debug("Save Shop");

		//Get data from View
		_shop.setTitle(_createShopView.getTitle());
		_shop.setAddress(_createShopView.getAddress());
		_shop.setParent(_createShopView.getBranding());
		_shop.setCategory(_createShopView.getCategory());

		//infox
		//destroy all
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateShopActivity.class));
		InfoBoxShowEvent emptyTitleInfo = new InfoBoxShowEvent(CreateShopActivity.class, "Title must not be empty", INFOTYPE.ERROR);


		if(!_shop.getTitle().isEmpty() && !_shop.getTitle().trim().equals("")){

			final InfoBoxShowEvent trySaving = new InfoBoxShowEvent(CreateShopActivity.class, "saving...", INFOTYPE.INFO,0);
			_clientFactory.getEventBus().fireEvent(trySaving);


			_clientFactory.getShopService().saveShop(_clientFactory.getAccountPersistor().getSessionId(), _shop, new AsyncCallback<Shop>() {

				@Override
				public void onFailure(Throwable caught) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));

					try{
						throw caught;
					}catch (UserNotLoggedInException e){
						Log.warn(e.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Please login or create new user to save.", INFOTYPE.ERROR));
					}catch (Throwable e){
						Log.error(e.getMessage());
					}

				}

				@Override
				public void onSuccess(Shop result) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Product save successfull.", INFOTYPE.SUCCESS));

					Log.debug("Shop save successful" + result);

					//redirect
					if(_place.getRedirectId()!=null){
						goTo(new CreateReceiptPlace(_place.getId(), result.getId(), "shop"));
					}else{
						updateView(result);
					}

					//setReadable
					_createShopView.setReadOnly(true);
				}
			});
		}else{
			_clientFactory.getEventBus().fireEvent(emptyTitleInfo);
		}


	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_shop=new Shop();
		Log.debug("activity startet");
		_createShopView = _clientFactory.getCreateShopView();
		_createShopView.setPresenter(this);

		if (_place.getId() != null && _place.getRedirectId()==null) {
			// Existing product... trying to load
			_clientFactory.getShopService().getShop(_place.getId(), _place.getRevision(),
					new AsyncCallback<Shop>() {

				@Override
				public void onFailure(Throwable caught) {
					try{
						throw caught;
					}catch (DaoException e){
						Log.error("DaoException at getShop: "+caught.getMessage());
					}catch (Throwable e){
						Log.error("Unexpected exception: "+caught.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
					}
				}

				@Override
				public void onSuccess(Shop result) {
					Log.debug("got shop: " + result);
					updateView(result);
					panel.setWidget(_createShopView);

					//setReadable
					_createShopView.setReadOnly(true);
					
					if(result.getParent()==null){
						_createShopView.setStatisticLatLng(
								result.getAddress().getLat(),
								result.getAddress().getLng());
					}else{
						_createShopView.setStatisticLatLng(
								Double.parseDouble(_place.getLat()), 
								Double.parseDouble(_place.getLng()));
					}
					/*
					onStatisticChangedEvent(
							_createShopView.getStatisticBoundingBox(), 
							_createShopView.getStatisticBeginDate(), 
							_createShopView.getStatisticEndDate());
						*/
				}
			});


		} else {
			// new product... reseting view
			Log.debug("Create new shop");

			//setTitle
			_shop.setTitle(_place.getTitle());
			
			//setLatlng from url
			if(_place.getLat()!=null && _place.getLng()!=null)
			{
				Log.debug("lat: "+_place.getLat()+", lng: "+_place.getLng());
				_shop.setAddress(new Address(null, Double.parseDouble(_place.getLat()), Double.parseDouble(_place.getLng())));
			}
			
			//Get Branding data from server and add it to the shop
			if(_place.getRedirectId()!=null && _place.getBrandId()!=null){
				_clientFactory.getShopService().getShop(_place.getBrandId(), new AsyncCallback<Shop>() {

					@Override
					public void onSuccess(Shop result) {
						_shop.setParent(result);
						
						updateView(_shop);
					}

					@Override
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.error("DaoException at getShop: "+caught.getMessage());
						}catch (Throwable e){
							Log.error("Unexpected exception: "+caught.getMessage());
							_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
						}
					}

				});
			}
			updateView(_shop);


			panel.setWidget(_createShopView);


			//setReadable
			_createShopView.setReadOnly(false);


		}

	}




	private void updateView(Shop shop){
		_shop = shop;
		_createShopView.setTitle(shop.getTitle());
		_createShopView.setAddress(shop.getAddress());
		_createShopView.setBranding(shop.getParent());
		_createShopView.setCategory(shop.getCategory());
	}


	@Override
	public void brandingSearch(String search) {

		_clientFactory.getSearchService().searchShop(search, null, new AsyncCallback<List<Shop>>() {

			@Override
			public void onSuccess(List<Shop> results) {
				Log.debug("search OK. Count: "+results.size());
				_createShopView.setBrandingSearchResults(results);
			}

			@Override
			public void onFailure(Throwable e) {
				Log.error("ShopSerachError: "+e);
			}
		});




	}


	@Override
	public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end) {
		Log.debug("onStatisticChangedEvent: bbox: "+bbox+", begin: "+begin+", end: "+end);
		_statisticDebounce++;
		final int curDebounce=_statisticDebounce;
		
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateShopActivity.class));
		final InfoBoxShowEvent loadingInfo = new InfoBoxShowEvent(CreateShopActivity.class, "Getting statistic data: ", INFOTYPE.INFO,0);
		_clientFactory.getEventBus().fireEvent(loadingInfo);

		_clientFactory.getSearchService().searchShopPrices(_shop.getId(), bbox, begin, end, new AsyncCallback<List<StatisticResult>>() {

			@Override
			public void onSuccess(List<StatisticResult> response) {
				if(curDebounce==_statisticDebounce){
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(loadingInfo));
					_createShopView.setStatisticResults(response);
				}
			}

			@Override
			public void onFailure(Throwable e) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateShopActivity.class));
				Log.error("searchproblem: "+e);
			}
		});
	}


	@Override
	public void onCategorySelectedEvent() {
		Log.debug("Unit has changed");		
	}

}

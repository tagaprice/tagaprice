package org.tagaprice.client.features.shopmanagement.createShop;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.*;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.NotificationMole;
import com.google.gwt.user.client.ui.PopupPanel;

public class CreateShopActivity implements ICreateShopView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateShopActivity.class);

	private Shop _shop;
	private ICreateShopView<ReceiptEntry> _createShopView;
	private CreateShopPlace _place;
	private ClientFactory _clientFactory;

	public CreateShopActivity(CreateShopPlace place, ClientFactory clientFactory) {
		CreateShopActivity._logger.log("CreateProductActivity created");
		_place = place;
		_clientFactory = clientFactory;
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
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_shop=null;
		CreateShopActivity._logger.log("activity startet");
		_createShopView = _clientFactory.getCreateShopView();
		_createShopView.setPresenter(this);
		_createShopView.reset();

		if (_place.getId() != null) {
			// Existing product... trying to load
			_clientFactory.getShopService().getShop(_place.getId(), _place.getRevision(),
					new AsyncCallback<Shop>() {

				@Override
				public void onSuccess(Shop result) {
					CreateShopActivity._logger.log("got shop: " + result);
					for(Shop s: result.getChildren())
						CreateShopActivity._logger.log("child: " + s.getTitle());

					updateView(result);
					//_createShopView.setReceiptEntries(result);
					panel.setWidget(_createShopView);
				}

				@Override
				public void onFailure(Throwable caught) {

					CreateShopActivity._logger.log(caught.getMessage());
				}
			});


		} else {
			// new product... reseting view
			CreateShopActivity._logger.log("Create new shop");

			if(_clientFactory.getAccountPersistor().getAddress()==null){
				_clientFactory.getEventBus().fireEvent(new WaitForAddressEvent());
			}else{
				_createShopView.setCurrentAddress(_clientFactory.getAccountPersistor().getAddress());
			}

			_clientFactory.getEventBus().addHandler(AddressChangedEvent.TYPE, new AddressChangedEventHandler() {

				@Override
				public void onAddressChanged(AddressChangedEvent event) {
					_createShopView.setCurrentAddress(event.getAddress());
				}
			});

			updateView(new Shop(""));
			panel.setWidget(_createShopView);
		}




	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent() {
		_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateShopActivity.class, "Try to save shop", INFOTYPE.INFO));


		_clientFactory.getShopService().saveShop(getShop(), new AsyncCallback<Shop>() {

			@Override
			public void onSuccess(Shop result) {
				CreateShopActivity._logger.log("got updated shop: " + result);
				updateView(result);
			}

			@Override
			public void onFailure(Throwable caught) {

				try{
					throw caught;
				}catch (UserNotLoggedInException e){
					//TODO This stuff must be implementet at an global place
					final PopupPanel pop = new PopupPanel();
					final NotificationMole mole = new NotificationMole();
					pop.show();
					pop.setPopupPosition(Window.getClientWidth() / 2, Window.getClientHeight() / 2);
					pop.add(mole);
					mole.setMessage("user not logged in "+e.getMessage());
					mole.setAnimationDuration(500);
					mole.show();

					Timer t = new Timer() {
						@Override
						public void run() {
							mole.hide();
							Timer t2 = new Timer() {

								@Override
								public void run() {
									pop.hide();
								}
							};
							t2.schedule(500);
						}
					};

					t.schedule(2000);
					CreateShopActivity._logger.log(e.getMessage());
				}catch (Throwable e){
					// last resort -- a very unexpected exception
					CreateShopActivity._logger.log(e.getMessage());
					e.printStackTrace();
				}

				CreateShopActivity._logger.log("got exception");
				CreateShopActivity._logger.log(caught.getMessage());
			}
		});
	}


	private Shop getShop() {
		Shop shop;
		if (_shop != null) {
			shop = _shop;
		} else {
			shop = new Shop();
		}
		shop.setTitle(_createShopView.getShopTitle());
		shop.setChildren(_createShopView.getChilds());
		return shop;
	}

	private void updateView(Shop shop){
		_shop = shop;
		_createShopView.setShopTitle(shop.getTitle());
		_createShopView.setChilds(_shop.getChildren());
	}

}

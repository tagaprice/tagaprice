package org.tagaprice.client;

import org.tagaprice.client.features.accountmanagement.login.LoginPlace;
import org.tagaprice.client.generics.events.AddressChangedEvent;
import org.tagaprice.client.generics.events.AddressChangedEventHandler;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxDestroyEventHandler;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.WaitForAddressEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.events.InfoBoxShowEventHandler;
import org.tagaprice.client.generics.events.WaitForAddressEventHandler;
import org.tagaprice.client.mvp.AppActivityMapper;
import org.tagaprice.client.mvp.AppPlaceHistoryMapper;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * GWT STARTPOINT - This is the class with the EntryPoint.
 * @author Helga Weik (kaltra)
 *
 */
public class TagAPrice implements EntryPoint {
	private static MyLogger logger = LoggerFactory.getLogger(TagAPrice.class);

	private ClientFactory clientFactory;
	private EventBus eventBus;


	private IUi _iui = new UIDesktop();

	/**
	 * Initializes ActivityManager and ActivityMapper for each display-area.
	 * For now, we have one display-area.
	 */
	@Override
	public void onModuleLoad() {
		TagAPrice.logger.log("EntryPoint startet");
		clientFactory = GWT.create(ClientFactory.class);
		eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();


		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper,
				eventBus);

		AppPlaceHistoryMapper historyMapper = GWT
		.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(placeController, eventBus, new LoginPlace());


		//RootLayoutPanel.get().add(completeScreen);
		RootLayoutPanel.get().add(_iui.getUI(activityManager, clientFactory));
		historyHandler.handleCurrentHistory();



		eventBus.addHandler(InfoBoxShowEvent.TYPE, new InfoBoxShowEventHandler() {
			@Override
			public void onNewInfo(InfoBoxShowEvent event) {
				_iui.getInfoBox().addInfoBoxEvent(event);
			}

		});

		eventBus.addHandler(InfoBoxDestroyEvent.TYPE, new InfoBoxDestroyEventHandler() {

			@Override
			public void onDestroyInfo(InfoBoxDestroyEvent event) {
				TagAPrice.logger.log("Destroy event:");
				_iui.getInfoBox().removeInfoBoxEvent(event);
			}
		});


		//AddressChangeHandler
		eventBus.addHandler(AddressChangedEvent.TYPE, new AddressChangedEventHandler() {

			@Override
			public void onAddressChanged(AddressChangedEvent event) {
				clientFactory.getAccountPersistor().setAddress(event.getAddress());

				_iui.getInfoBox().addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Address Updated: "+event.getAddress(), INFOTYPE.SUCCESS));
			}
		});

		eventBus.addHandler(WaitForAddressEvent.TYPE, new WaitForAddressEventHandler() {

			@Override
			public void onAddressChanged(WaitForAddressEvent event) {
				locateMe();
			}
		});



		//Start Account Initialisation
		//clientFactory.getAccountPersistor().getAddress();

		//Get Position if no one is saved in the cookies.
		if(clientFactory.getAccountPersistor().getAddress()==null){
			eventBus.fireEvent(new WaitForAddressEvent());
		}


		//Test if user is logged in
		if(!clientFactory.getAccountPersistor().isLoggedIn()){
			eventBus.fireEvent(new LoginChangeEvent(false));
		}else{
			eventBus.fireEvent(new LoginChangeEvent(true));

			//TODO Get address from user via rpc and send AddressChanged event
		}



	}

	private void locateMe(){
		_iui.getInfoBox().addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Try to update address", INFOTYPE.INFO));


		Geolocation.getGeolocation().getCurrentPosition(new PositionCallback() {

			@Override
			public void onSuccess(final Position position) {


				//user OSM
				clientFactory.getSearchService().searchAddress(position.getCoords().getLatitude(), position.getCoords().getLongitude(),
						new AsyncCallback<Address>() {

					@Override
					public void onSuccess(Address result) {
						// TODO Auto-generated method stub
						_iui.getInfoBox().addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Something was ok: "+result, INFOTYPE.INFO));
						eventBus.fireEvent(new AddressChangedEvent(result));

					}

					@Override
					public void onFailure(Throwable e) {
						_iui.getInfoBox().addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Can't find address", INFOTYPE.ERROR,0));
					}
				});


			}

			@Override
			public void onFailure(PositionError error) {
				_iui.getInfoBox().addInfoBoxEvent(new InfoBoxShowEvent(TagAPrice.class, "Can't find address", INFOTYPE.ERROR,0));

			}
		});
	}

}

package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.ShopDTO;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity{

	MyLogger logger = LoggerFactory.getLogger(CreateReceiptActivity.class);

	private CreateReceiptPlace _place ;
	private ClientFactory _clientFactory ;
	private ICreateReceiptView<IReceiptEntry> createReceiptView;

	private IShop shop;
	private ArrayList<IReceiptEntry> receiptEntries;
	private HashMap<String, IReceiptEntry> receiptEntriesViaProduct = new HashMap<String, IReceiptEntry>();

	HandlerRegistration hr;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		logger.log("CreateReceiptActivity created");
		this._place = place;
		this._clientFactory =clientFactory;
		createReceiptView = this._clientFactory.getCreateReceiptView();
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
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		logger.log("start");

		//load Shops
		this._clientFactory.getShopService().getShops(null, new AsyncCallback<ArrayList<IShop>>() {

			@Override
			public void onSuccess(ArrayList<IShop> result) {
				createReceiptView.setAvailableShops(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				logger.log("ERROR receiving Shops");
			}
		});

		this.createReceiptView.addShopChangeHanlder(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				_clientFactory.getShopService().getShop(createReceiptView.getShop().getRevisionId(), new AsyncCallback<ShopDTO>() {

					@Override
					public void onSuccess(ShopDTO result) {
						// TODO Auto-generated method stub
						createReceiptView.shopsLoaded("shop " + result.getShop().getTitle() + " loaded successfully");
						shop = result.getShop();
						receiptEntries = result.getReceiptEntries();
						//Speichern in HasMap - String: id_Title

						MultiWordSuggestOracle mwso = new MultiWordSuggestOracle();
						for(IReceiptEntry re: receiptEntries) {
							String combo = re.getProduct().getRevisionId().getId() + "_" + re.getProduct().getTitle();

							mwso.add(combo);
							receiptEntriesViaProduct.put(combo, re);

						}
						mwso = new MultiWordSuggestOracle(",.");
						mwso.add("coke");
						mwso.add("cola");

						createReceiptView.setSuggestProducts(mwso);

					}

					@Override
					public void onFailure(Throwable caught) {
						createReceiptView.shopsLoaded("error loading shops");
					}
				});
				createReceiptView.shopsLoaded("shop loaded successfully");

			}
		});

		panel.setWidget(createReceiptView.asWidget());

	}



	@Override
	public void onAddEntry() {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSave() {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSaveEvent() {
		// TODO Auto-generated method stub

	}

}

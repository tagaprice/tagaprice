package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
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
	private ArrayList<IReceiptEntry> existingReceiptEntries;
	private HashMap<String, IReceiptEntry> existingReceiptEntriesViaProduct = new HashMap<String, IReceiptEntry>();
	private ArrayList<IReceiptEntry> newReceiptEntries = new ArrayList<IReceiptEntry>();
	HandlerRegistration hr;

	IReceipt receipt = new Receipt();

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

		createReceiptView.setPresenter(this);

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
				_clientFactory.getShopService().getShop(createReceiptView.getShop().getRevisionId(), new AsyncCallback<IShop>() {

					@Override
					public void onSuccess(IShop result) {
						// TODO Auto-generated method stub
						createReceiptView.shopsLoaded("shop " + result.getTitle() + " loaded successfully");
						shop = result;
						//Speichern in HasMap - String: id_Title

						MultiWordSuggestOracle mwso = new MultiWordSuggestOracle(",._");
						for(IReceiptEntry re: existingReceiptEntries) {
							String combo = re.getProduct().getRevisionId().getId() + "_" + re.getProduct().getTitle();

							mwso.add(combo);
							existingReceiptEntriesViaProduct.put(combo, re);

						}

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

		//Register onChangeHandler with _products field

		panel.setWidget(createReceiptView.asWidget());

	}




	@Override
	public void onAddEntry() {
		logger.log("onAddEntry: looking for product: " + createReceiptView.getProductName());
		IReceiptEntry re = this.existingReceiptEntriesViaProduct.get(createReceiptView.getProductName());
		if(re == null) {
			logger.log("found NO product");
			return;
		}
		logger.log("found product!");

		IProduct product = re.getProduct();

		long price = createReceiptView.getPrice();
		int quantity = createReceiptView.getQuantity();

		IReceiptEntry newRE = new ReceiptEntry(quantity, price, product, receipt, product.getRevisionId(), receipt.getRevisionId());

		createReceiptView.setPrice(0L);
		createReceiptView.setQuantity(0);

		newReceiptEntries.add(newRE);

		createReceiptView.addReceiptEntry(newReceiptEntries);
	}



	@Override
	public void onSave() {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSaveEvent() {
		// TODO Auto-generated method stub

	}



	@Override
	public void onSelectProduct() {
		logger.log("onSelectProduct: looking for product: " + createReceiptView.getProductName());
		IReceiptEntry re = this.existingReceiptEntriesViaProduct.get(createReceiptView.getProductName());
		if(re == null) {
			logger.log("found NO product");
			return;
		}
		logger.log("found product!");
		createReceiptView.setPrice(re.getPrice());
		createReceiptView.setQuantity(1);

	}

}

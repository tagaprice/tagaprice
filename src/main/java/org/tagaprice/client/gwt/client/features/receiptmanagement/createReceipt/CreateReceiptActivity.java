package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceipt;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.Receipt;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Address;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateReceiptActivity implements ICreateReceiptView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateReceiptActivity.class);

	private CreateReceiptPlace _place;
	private ClientFactory _clientFactory;
	private IReceipt _receipt;
	private ICreateReceiptView _createReceiptView;

	public CreateReceiptActivity(CreateReceiptPlace place, ClientFactory clientFactory) {
		CreateReceiptActivity._logger.log("CreateProductActivity created");
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
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		// TODO Auto-generated method stub
		CreateReceiptActivity._logger.log("activity startet");
		_createReceiptView = _clientFactory.getCreateReceiptView();
		//panel.setWidget(new HTML("Impl here some cool stuff"));
		panel.setWidget(_createReceiptView);

		Random random = new Random(1654196865);
		IRevisionId r1 = new RevisionId(random.nextLong(), 1);
		IRevisionId r2 = new RevisionId(random.nextLong(), 1);
		IAddress tempAddres = new Address(r2, "Holzhausergasse 9", "1020", "Vienna", Country.at, 48.21975481443672, 16.38885498046875);
		IShop tempshop = new Shop(r1, "Billa");
		tempAddres.setShop(tempshop);

		//Create test product
		//IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<IReceiptEntry>());
		IReceipt tempReceipt = new Receipt("First Receipt", new Date(), tempAddres, new ArrayList<IReceiptEntry>());
		updateView(tempReceipt);
	}

	private void updateView(IReceipt receipt){
		_receipt=receipt;

		_createReceiptView.setPresenter(this);
		_createReceiptView.setTitle(_receipt.getTitle());
		_createReceiptView.setDate(_receipt.getDate());
		_createReceiptView.setAddress(_receipt.getAddress());
		_createReceiptView.setReceiptEntries(_receipt.getReceiptEntries());
	}

}

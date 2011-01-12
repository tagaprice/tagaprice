package org.tagaprice.client.gwt.client.features.productmanagement.createProduct;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;
import org.tagaprice.client.gwt.shared.entities.dump.Unit;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateProductActivity implements ICreateProductView.Presenter, Activity {
	private static final MyLogger _logger = LoggerFactory.getLogger(CreateProductActivity.class);

	private CreateProductPlace _place;
	private ClientFactory _clientFactory;

	public CreateProductActivity(CreateProductPlace place, ClientFactory clientFactory) {
		CreateProductActivity._logger.log("CreateProductActivity created");
		_place=place;
		_clientFactory=clientFactory;
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
		CreateProductActivity._logger.log("activity startet");

		if(_place.getRevisionId().getId()==null){
			CreateProductActivity._logger.log("Create new Product");
			panel.setWidget(_clientFactory.getCreateProductView());



			//panel.setWidget(new Label("Create new Product"));
		}else if(_place.getRevisionId().getId()!=null){
			CreateProductActivity._logger.log("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision());
			//panel.setWidget(new Label("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision()));
			ICreateProductView createProductView = _clientFactory.getCreateProductView();
			panel.setWidget(createProductView);


			//Add test data
			createProductView.setTitle("superTest");
			IQuantity quantity = new Quantity();
			quantity.setQuantity(1.1);


			quantity.setUnit(Unit.kg);
			createProductView.setQuantity(quantity);
		}

	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEvent(ClickEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTitleSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnitSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCategorySelectedEvent() {
		// TODO Auto-generated method stub

	}

}

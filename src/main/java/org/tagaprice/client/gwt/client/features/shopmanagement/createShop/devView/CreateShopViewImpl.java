package org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView;

import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateShopViewImpl extends Composite implements ICreateShopView {
	interface CreateShopViewImpleUiBinder extends
	UiBinder<Widget, CreateShopViewImpl> {
	}

	private static CreateShopViewImpleUiBinder uiBinder = GWT
	.create(CreateShopViewImpleUiBinder.class);

	private Presenter _presenter;

	@UiField
	Button saveButton;



	public CreateShopViewImpl() {
		initWidget(CreateShopViewImpl.uiBinder.createAndBindUi(this));


		//Implement listener
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent(arg0);
			}
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter = presenter;
	}

}

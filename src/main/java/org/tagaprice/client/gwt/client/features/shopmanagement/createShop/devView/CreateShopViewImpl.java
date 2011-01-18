package org.tagaprice.client.gwt.client.features.shopmanagement.createShop.devView;

import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class CreateShopViewImpl extends Composite implements ICreateShopView {

	private Presenter _presenter;

	public CreateShopViewImpl() {
		initWidget(new Label("create layout here"));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

}

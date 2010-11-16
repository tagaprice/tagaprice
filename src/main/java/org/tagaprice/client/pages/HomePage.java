/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: HomePage.java
 * Date: 18.07.2010
 */
package org.tagaprice.client.pages;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.ImageBundle;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * 
 *
 */
public class HomePage extends APage {

	TaPManager _Mng = TaPManager.getInstance();

	Grid _grid = new Grid(2, 3);
	ImageTextButton _newReceipt = new ImageTextButton("add Receipt", new Image(
			ImageBundle.INSTANCE.productPriview()), "receipt/get");

	public HomePage() {
		init(_grid);
		setStyleName("HomePage");

		_grid.setWidth("100%");
		_grid.setStyleName("HomePage-Grid");
		// grid.setBorderWidth(1);
		_grid.getCellFormatter().setWidth(0, 0, "33%");
		_grid.getCellFormatter().setWidth(0, 1, "33%");
		_grid.getCellFormatter().setWidth(0, 2, "33%");
		_grid.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getCellFormatter().setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getCellFormatter().setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getCellFormatter().setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getCellFormatter().setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_CENTER);
		_grid.getCellFormatter().setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_CENTER);

		//

		_grid.setWidget(0, 0, new ImageTextButton("add Product", new Image(
				ImageBundle.INSTANCE.productPriview()), "product/new"));
		_grid.setWidget(0, 1, new ImageTextButton("add Shop", new Image(
				ImageBundle.INSTANCE.newShopButton()), "shop/new"));

		if (Cookies.getCookie("TaPSId") != null) { // / TODO check session
													// validity
			_grid.setWidget(0, 2, new ImageTextButton("Logout", new Image(
					ImageBundle.INSTANCE.loginButton()), "user/logout"));
			_grid.setWidget(1, 0, new ImageTextButton("add Receipt", new Image(
					ImageBundle.INSTANCE.productPriview()), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					TaPManager.getInstance().getUIManager().waitingPage();

					try {
						RPCHandlerManager.getReceiptHandler().save(null,
								new AsyncCallback<ReceiptData>() {

									@Override
									public void onSuccess(ReceiptData result) {
										History.newItem("receipt/get&id="
												+ result.getId());

									}

									@Override
									public void onFailure(Throwable caught) {
										showInfo("Fail: " + caught,
												BoxType.WARNINGBOX);
									}
								});
					} catch (IllegalArgumentException e) {
						showInfo("Fail: " + e, BoxType.WARNINGBOX);
					} catch (InvalidLoginException e) {
						showInfo("Fail: " + e, BoxType.WARNINGBOX);
					}

				}
			}));
			_grid.setWidget(1, 1, new ImageTextButton("My Receipts", new Image(
					ImageBundle.INSTANCE.productPriview()), "receipt/list"));
		} else {
			_grid.setWidget(0, 2, new ImageTextButton("Login", new Image(
					ImageBundle.INSTANCE.loginButton()), "user/login"));
			_grid.setWidget(1, 0, new ImageTextButton("Sign Up", new Image(
					ImageBundle.INSTANCE.loginButton()),
					"user/registration/new"));
		}

	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub

	}
}

class ImageTextButton extends Composite {
	FlowPanel _verticalPanael1 = new FlowPanel();
	Label _label;
	Image _image;

	public ImageTextButton(String text, Image pic, ClickHandler handler) {
		init(text, pic);
		addClickHandler(handler);
	}

	public ImageTextButton(String text, Image pic, final String link) {
		init(text, pic);

		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				History.newItem(link);
			}
		});

	}

	private void init(String text, Image pic) {
		initWidget(_verticalPanael1);
		_label = new Label(text);
		_image = pic;

		_verticalPanael1.add(_image);
		_verticalPanael1.add(_label);

		setStyleName("button");
	}

	private void addClickHandler(ClickHandler handler) {
		addDomHandler(handler, ClickEvent.getType());
	}
}

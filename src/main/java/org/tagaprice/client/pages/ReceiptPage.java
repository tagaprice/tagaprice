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
 * Filename: ReceiptWidget.java
 * Date: 15.05.2010
 */
package org.tagaprice.client.pages;


import java.util.ArrayList;

import org.tagaprice.client.*;
import org.tagaprice.client.pages.previews.*;
import org.tagaprice.client.widgets.*;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.SelectiveListWidget.SelectionType;
import org.tagaprice.shared.entities.*;
import org.tagaprice.shared.entities.PropertyTypeDefinition.Datatype;
import org.tagaprice.shared.enums.SearchType;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Displays editable receipt including shop and product search.
 *
 */
public class ReceiptPage extends AInfoBoxComposite {
	interface MyUiBinder extends UiBinder<Widget, ReceiptPage>{}
	private MyUiBinder _uiBinder = GWT.create(MyUiBinder.class);


	boolean _isEditable=true;
	//	MorphWidget title = new MorphWidget("Default title", Datatype.STRING, isEditable);
	int _bill=0;
	ChangeHandler _priceChangeHandler;
	Receipt _receiptData;
	ShopPagePreview _shopPreview;
	boolean _allowSaving = false;

	private SearchWidget _shopChooser2 = new SearchWidget(SearchType.SHOP, true, false, SelectionType.PLUSBUTTON);
	private SearchWidget _productChooser2;

	SelectiveListWidget _productContainer = new SelectiveListWidget(SelectionType.MINUSBUTTON);


	@UiField VerticalPanel _basePanel;
	@UiField HorizontalPanel _top;
	@UiField DateWidget _date;
	@UiField(provided=true) MorphWidget _title=new MorphWidget("Default title", Datatype.STRING, _isEditable);
	@UiField HorizontalPanel _pricePanel;
	@UiField SimplePanel _shop;
	@UiField SimplePanel _veProductContainer;
	@UiField SimplePanel _product;
	@UiField Label _price;
	@UiField Button _save;

	/**
	 * Creates a Receipt Page with receipt data
	 * @param receiptData
	 * @param editable
	 * @param text
	 */
	public ReceiptPage(Receipt receiptData, boolean editable, boolean text){
		//this();

		this._receiptData=receiptData;
		_veProductContainer.setWidget(_productContainer);

		_isEditable=editable;
		_title = new MorphWidget(receiptData.getTitle(), Datatype.STRING, true);

		_date.setDate(receiptData.getDate());

		if(receiptData.getShop()!=null){
			setShop(receiptData.getShop());
		}

		for(Product pd: receiptData.getProducts()){
			addProduct(pd);
		}

		refreshPrice();
	}

	/**
	 * 
	 */
	public ReceiptPage(Receipt receiptData1, boolean editable) {
		init(_uiBinder.createAndBindUi(this));

		_save.setVisible(false);

		this._receiptData=receiptData1;

		//Style
		_basePanel.setWidth("100%");

		_top.setWidth("100%");
		_top.setCellWidth(_date, "50px");

		_title.setWidth("100%");

		//shopChooser
		_shop = new SimplePanel();
		_shop.setWidget(_shopChooser2);
		_basePanel.insert(_shop, 2);
		_basePanel.setCellHorizontalAlignment(_pricePanel, HasHorizontalAlignment.ALIGN_RIGHT);






		//Listen on Select
		_shopChooser2.getSelectiveVerticalPanel().addSelectiveListHandler(new ISelectiveListHandler() {

			@Override
			public void onClick(Widget widget, int index) {
				setShop(((ShopPagePreview)widget).getShopData());
			}
		});


		//ProductsHandler
		_priceChangeHandler = new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				refresh();
			}
		};


		//------
		_veProductContainer.setWidget(_productContainer);


		_isEditable=editable;
		_title = new MorphWidget(_receiptData.getTitle(), Datatype.STRING, true);

		_date.setDate(_receiptData.getDate());

		if(_receiptData.getShop()!=null){
			setShop(_receiptData.getShop());
		}

		for(Product pd: _receiptData.getProducts()){
			addProduct(pd);
		}


		//------

		//Products
		_productContainer.addSelectiveListHandler(new ISelectiveListHandler() {

			@Override
			public void onClick(Widget widget, int index) {
				_productContainer.removeWidget(index);
				refresh();

			}
		});



		//Save
		_save.setStyleName("Awesome");
		_save.setWidth("100%");
		_save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				_receiptData.setDraft(false);//Now a new receipt will be created.
				//Save where Draft(false);
				if(_allowSaving){
					try {
						RPCHandlerManager.getReceiptHandler().save(getReceiptData(), new AsyncCallback<Receipt>() {

							@Override
							public void onSuccess(Receipt result) {
								_receiptData=result;
								//receiptData._setRev(result.getRev());
								showInfo("Succesfull saved", BoxType.WARNINGBOX);
								Timer close = new Timer() {

									@Override
									public void run() {
										hideInfo();
									}
								};

								close.schedule(1000);
							}

							@Override
							public void onFailure(Throwable caught) {
								showInfo("Save Problem: "+caught, BoxType.WARNINGBOX);
							}
						});
					} catch (IllegalArgumentException e) {
						showInfo("Save Problem: "+e, BoxType.WARNINGBOX);
					} catch (InvalidLoginException e) {
						showInfo("Save Problem: "+e, BoxType.WARNINGBOX);
					}
				}
			}
		});

		refreshPrice();
		_allowSaving=true;
	}





	/**
	 * Refresh all data and save receipt data
	 */
	public void refresh(){
		refreshPrice();
		if(_allowSaving){
			try {
				RPCHandlerManager.getReceiptHandler().save(getReceiptData(), new AsyncCallback<Receipt>() {

					@Override
					public void onSuccess(Receipt result) {
						_receiptData=result;
						showInfo("Succesfull saved", BoxType.WARNINGBOX);
						Timer close = new Timer() {

							@Override
							public void run() {
								hideInfo();
							}
						};

						close.schedule(1000);
					}

					@Override
					public void onFailure(Throwable caught) {
						showInfo("Save Problem: "+caught, BoxType.WARNINGBOX);
					}
				});
			} catch (IllegalArgumentException e) {
				showInfo("Save Problem: "+e, BoxType.WARNINGBOX);
			} catch (InvalidLoginException e) {
				showInfo("Save Problem: "+e, BoxType.WARNINGBOX);
			}
		}
		//Save Draft or Receipt
	}

	/**
	 * 
	 */
	private void refreshPrice(){
		_bill=0;
		for(int i=0;i<_productContainer.getWidgetCount();i++){
			_bill+=((ProductPagePreview)_productContainer.getWidget(i)).getProductData().getAvgPrice().getAmount();
		}

		_price.setText((_bill/100.00)+"");
	}

	/**
	 * Sets shop data
	 * @param _shop
	 */
	public void setShop(Shop shopData){
		_shopPreview=new ShopPagePreview(shopData, _isEditable);
		_shop.setWidget(_shopPreview);

		_product=new SimplePanel();
		_productChooser2 = new SearchWidget(true, true, SelectionType.PLUSBUTTON, shopData);
		_product.setWidget(_productChooser2);
		_basePanel.insert(_product, 4);

		//ProductChooserListener
		_productChooser2.getSelectiveVerticalPanel().addSelectiveListHandler(new ISelectiveListHandler() {
			@Override
			public void onClick(Widget widget, int index) {
				addProduct(((ProductPagePreview)widget).getProductData());
				_productChooser2.hideSuggest();
			}
		});

		_save.setVisible(true);

	}

	/**
	 * Sets a new Shop
	 */
	public void setNewShop(){
		_shop.setWidget(new ShopPagePreview(null, true));
	}

	/**
	 * 
	 * @param product
	 */
	public void addProduct(Product product){
		_productContainer.add(new ProductPagePreview(product, _isEditable, _priceChangeHandler));
		refresh();
	}
	/**
	 * Returns receipt data
	 * @return Receipt
	 */

	public Receipt getReceiptData(){
		_receiptData.setDate(_date.getDate());
		_receiptData.setTitle(_title.getValue());
		_receiptData.setBill(_bill);

		if(_shopPreview!=null){
			_receiptData.setShop(_shopPreview.getShopData());
		}

		ArrayList<Product> productList = new ArrayList<Product>();
		for(int i=0;i<_productContainer.getWidgetCount();i++){
			productList.add(((ProductPagePreview)_productContainer.getWidget(i)).getProductData());
		}
		_receiptData.setProductData(productList);

		return _receiptData;
	}

}

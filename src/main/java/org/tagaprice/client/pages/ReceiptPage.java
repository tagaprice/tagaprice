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

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.InfoBoxComposite;
import org.tagaprice.client.ProductPreview;
import org.tagaprice.client.SearchWidget;
import org.tagaprice.client.SelectiveVerticalPanel;
import org.tagaprice.client.SelectiveVerticalPanelHandler;
import org.tagaprice.client.ShopPreview;
import org.tagaprice.client.InfoBox.BoxType;
import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.client.SelectiveVerticalPanel.SelectionType;
import org.tagaprice.client.widgets.DateWidget;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ReceiptData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/** 
 * Displays edit able receipt including shop and product search.
 *
 */
public class ReceiptPage extends InfoBoxComposite {
	interface MyUiBinder extends UiBinder<Widget, ReceiptPage>{}
	private MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	
	boolean isEditable=true;
//	MorphWidget title = new MorphWidget("Default title", Datatype.STRING, isEditable);
	int bill=0;
	ChangeHandler priceChangeHandler; 
	ReceiptData receiptData;
	ShopPreview shopPreview;
	boolean allowSaving = false;
	
	private SearchWidget shopChooser2 = new SearchWidget(SearchType.SHOP, true, false, SelectionType.PLUSBUTTON);
	private SearchWidget productChooser2;
	
	SelectiveVerticalPanel productContainer = new SelectiveVerticalPanel(SelectionType.MINUSBUTTON);
	
	
	@UiField VerticalPanel basePanel;
	@UiField HorizontalPanel top;
	@UiField DateWidget date;
	@UiField(provided=true) MorphWidget title=new MorphWidget("Default title", Datatype.STRING, isEditable);
	@UiField HorizontalPanel pricePanel;
	@UiField SimplePanel shop;
	@UiField SimplePanel veProductContainer;
	@UiField SimplePanel product;
	@UiField Label price;
	@UiField Button save;
	
	/**
	 * 
	 * @param receiptData
	 */
	public ReceiptPage(ReceiptData receiptData, boolean editable, boolean text){
		//this();
		
		this.receiptData=receiptData;
		veProductContainer.setWidget(productContainer);
		
		isEditable=editable;
		title = new MorphWidget(receiptData.getTitle(), Datatype.STRING, true);
			
		date.setDate(receiptData.getDate());
		
		if(receiptData.getShop()!=null){
			setShop(receiptData.getShop());
		}
		
		for(ProductData pd: receiptData.getProductData()){
			addProduct(pd);
		}
		
		refreshPrice();	
	}
	
	/**
	 * 
	 */
	public ReceiptPage(ReceiptData _receiptData, boolean editable) {
		init(uiBinder.createAndBindUi(this));
		
		save.setVisible(false);
		
		this.receiptData=_receiptData;
		
		//Style
		basePanel.setWidth("100%");
		
		top.setWidth("100%");		
		top.setCellWidth(date, "50px");
		
		title.setWidth("100%");
		
		//shopChooser
		shop = new SimplePanel();
		shop.setWidget(shopChooser2);
		basePanel.insert(shop, 2);		
		basePanel.setCellHorizontalAlignment(pricePanel, HasHorizontalAlignment.ALIGN_RIGHT);

		
		
		
		
		
		//Listen on Select
		shopChooser2.getSelectiveVerticalPanel().addSelectiveVerticalPanelHandler(new SelectiveVerticalPanelHandler() {
			
			@Override
			public void onClick(Widget widget, int index) {
				setShop(((ShopPreview)widget).getShopData());
			}
		});
		
		
		//ProductsHandler
		priceChangeHandler = new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				refresh();		
			}
		};
		
		
		//------
		veProductContainer.setWidget(productContainer);
		
		
		isEditable=editable;
		title = new MorphWidget(receiptData.getTitle(), Datatype.STRING, true);
		
		date.setDate(receiptData.getDate());
		
		if(receiptData.getShop()!=null){
			setShop(receiptData.getShop());
		}
		
		for(ProductData pd: receiptData.getProductData()){
			addProduct(pd);
		}
		
		
		//------
		
		//Products		
		productContainer.addSelectiveVerticalPanelHandler(new SelectiveVerticalPanelHandler() {
			
			@Override
			public void onClick(Widget widget, int index) {
				productContainer.removeWidget(index);
				refresh();
				
			}
		});
			
		
		
		//Save
		save.setStyleName("Awesome");
		save.setWidth("100%");
		save.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				receiptData.setDraft(false);//Now a new receipt will be created.
				//Save where Draft(false);
				if(allowSaving){
					try {
						RPCHandlerManager.getReceiptHandler().save(getReceiptData(), new AsyncCallback<ReceiptData>() {
							
							@Override
							public void onSuccess(ReceiptData result) {
								receiptData=result;
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
		allowSaving=true;
	}
	
	
	
	
	
	/**
	 * Refresh all data and save data
	 */
	public void refresh(){
		refreshPrice();
		if(allowSaving){
			try {
				RPCHandlerManager.getReceiptHandler().save(getReceiptData(), new AsyncCallback<ReceiptData>() {
					
					@Override
					public void onSuccess(ReceiptData result) {
						receiptData=result;
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
		bill=0;
		for(int i=0;i<productContainer.getWidgetCount();i++){
			bill+=((ProductPreview)productContainer.getWidget(i)).getProductData().getAvgPrice().getPrice();
		}
		
		price.setText((bill/100.00)+"");
	}
	
	/**
	 * 
	 * @param shop
	 */
	public void setShop(ShopData shopData){
		shopPreview=new ShopPreview(shopData, isEditable);
		shop.setWidget(shopPreview);
		
		product=new SimplePanel();
		productChooser2 = new SearchWidget(SearchType.SHOP, true, true, SelectionType.PLUSBUTTON, shopData);
		product.setWidget(productChooser2);
		basePanel.insert(product, 4);
		
		//ProductChooserListener
		productChooser2.getSelectiveVerticalPanel().addSelectiveVerticalPanelHandler(new SelectiveVerticalPanelHandler() {			
			@Override
			public void onClick(Widget widget, int index) {
				addProduct(((ProductPreview)widget).getProductData());	
				productChooser2.hideSuggest();
			}
		});
		
		save.setVisible(true);

	}

	
	public void setNewShop(){
		shop.setWidget(new ShopPreview(null, true));
	}
	
	/**
	 * 
	 * @param product
	 */
	public void addProduct(ProductData product){
		productContainer.add(new ProductPreview(product, isEditable, priceChangeHandler));
		refresh();
	}
	
	
	public ReceiptData getReceiptData(){
		receiptData.setDate(date.getDate());	
		receiptData.setTitle(title.getText());
		receiptData.setBill(bill);		
		
		if(shopPreview!=null){
			receiptData.setShop(shopPreview.getShopData());
		}
		
		ArrayList<ProductData> productList = new ArrayList<ProductData>();		
		for(int i=0;i<productContainer.getWidgetCount();i++){
			productList.add(((ProductPreview)productContainer.getWidget(i)).getProductData());
		}		
		receiptData.setProductData(productList);
		
		return receiptData;
	}
	
}

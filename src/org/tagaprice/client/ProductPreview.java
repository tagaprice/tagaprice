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
 * Filename: ProductPreview.java
 * Date: 13.05.2010
*/
package org.tagaprice.client;


import org.tagaprice.shared.ProductData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays the most important properties of a Product.
 * Properties: title, rating, progress, price, quantity
 *
 */
public class ProductPreview extends EntityPreview {
	interface MyUiBinder extends UiBinder<Widget, ProductPreview>{}
	private MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	private ProductData productData;
	private boolean editable;
	private RatingWidget ratingWidget;
	private MorphWidget price;
	private MorphWidget quantitiy;
	private MorphWidget receipt;
	
	@UiField SimplePanel ratingPanel;
	@UiField HorizontalPanel HoPa1;
	@UiField HorizontalPanel HoPa2;
	@UiField SimplePanel quantitiyPanel;
	@UiField SimplePanel pricePanel;
	@UiField Label name;
	@UiField SimplePanel logoPanel;
	@UiField VerticalPanel VePa1;
	@UiField VerticalPanel VePa2;
	@UiField Label currency;
	@UiField Label unit;
	
	
	/**
	 * 
	 * @param productData
	 * @param editable
	 * @param handler
	 */
	public ProductPreview(ProductData productData, boolean editable, ChangeHandler handler){
		this(productData, editable);
		addChangeHandler(handler);
	}
	
	/**
	 * 
	 * @param productData 
	 * @param editable 
	 */
	public ProductPreview(ProductData _productData, boolean _editable) {
		productData=_productData;
		editable=_editable;
		initWidget(uiBinder.createAndBindUi(this));
		
		
		
		//VePa1
		VePa1.setWidth("100%");
		VePa1.setStyleName("ProductPreview");
		//HoPa1
		HoPa1.setWidth("100%");
		
		HoPa1.setCellWidth(logoPanel, "40px");
		//HoPa1.setBorderWidth(1);
		
		//VePa2
		VePa2.setSize("100%", "40px");
		//VePa2.setCellHeight(name, "100%");
		//VePa1.setBorderWidth(1);
		
		//SetValues
		
		
		//HoPa2
		HoPa2.setWidth("100%");		
		ratingWidget=new RatingWidget(productData.getRating(), editable);
		ratingPanel.setWidget(ratingWidget);
		HoPa2.setCellWidth(ratingPanel, "100%");
		

		
		logoPanel.add(new ProgressWidget(new Image(MyResources.INSTANCE.productPriview()), 50));
		logoPanel.setHeight(MyResources.INSTANCE.productPriview().getHeight()+"px");
		
		
		name.setText(productData.getName());
		price=new MorphWidget(""+(this.productData.getPrice()/100.00),editable);
		price.setWidth("40px");
		pricePanel.setWidget(price);
		
		quantitiy=new MorphWidget(productData.getQuantitiy(), editable);
		quantitiy.setWidth("40px");
		quantitiyPanel.setWidget(quantitiy);
		
		currency.setText(productData.getCurrency());
		unit.setText(productData.getUnit());
		
		if(editable && !productData.hasReceipt())
			noReceiptName();
		
		
		//AddListener
		addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("product/get&id="+productData.getId());			
			}
		});
	}
	

	/**
	 * 
	 */
	private void noReceiptName(){
		final VerticalPanel VePa1 = new VerticalPanel();
		VePa1.setWidth("100%");
		VePa1.setStyleName("Warning");
		receipt=new MorphWidget("Insert here missing receipt name!", editable);
		receipt.setWidth("100%");
		VePa1.add(receipt);
		this.VePa1.add(VePa1);
		

	}

	/**
	 * Return current productData
	 * @return 
	 */
	public ProductData getProductData(){
		if(editable){
			productData.setPrice((int)(Double.parseDouble(price.getText())*100));
			productData.setQuantitiy(quantitiy.getText());
			productData.setRating(ratingWidget.getRating());
		}
		
		return productData;
	}
	
	/**
	 * 
	 * @return Is ProductPreview editable
	 */
	public boolean isEditable(){
		return editable;
	}

	
	/**
	 * Handler is called when price change. 
	 * @param handler
	 */
	public void addChangeHandler(ChangeHandler handler){
		price.addChangeHandler(handler);
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void addClickHandler(ClickHandler handler){
		addDomHandler(handler, ClickEvent.getType());
	}
}

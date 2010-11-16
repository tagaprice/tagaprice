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
package org.tagaprice.client.pages.previews;


import org.tagaprice.client.ImageBundle;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.ProgressWidget;
import org.tagaprice.client.widgets.RatingWidget;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyDefinition.Datatype;

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
public class ProductPagePreview extends APagePreview {
	private interface _MyUiBinder extends UiBinder<Widget, ProductPagePreview>{}
	private _MyUiBinder _uiBinder = GWT.create(_MyUiBinder.class);
	private ProductData _productData;
	private boolean _editable;
	private RatingWidget _ratingWidget;
	private MorphWidget _price;
	private MorphWidget _quantitiy;
	private MorphWidget _receipt;
	
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
	public ProductPagePreview(ProductData productData, boolean editable, ChangeHandler handler){
		super();
		init(productData, editable);
		addChangeHandler(handler);
	}
	
	
	public ProductPagePreview(ProductData productData, boolean editable){
		init(productData, editable);
	}
	
	
	
	/**
	 * 
	 * @param productData 
	 * @param editable 
	 */
	public void init(ProductData productData, boolean editable) {
		_productData=productData;
		_editable=editable;
		initWidget(_uiBinder.createAndBindUi(this));
		
		
		
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
		_ratingWidget=new RatingWidget(_productData.getRating(), _editable);
		ratingPanel.setWidget(_ratingWidget);
		HoPa2.setCellWidth(ratingPanel, "100%");
		

		Image progressImage = new Image(ImageBundle.INSTANCE.productPriview()); 
		logoPanel.add(new ProgressWidget(progressImage, 50));
		logoPanel.setHeight(ImageBundle.INSTANCE.productPriview().getHeight()+"px");
		
		
		name.setText(_productData.getTitle());
		_price=new MorphWidget(""+(this._productData.getAvgPrice().getPrice()/100.00),Datatype.DOUBLE, _editable);
		_price.setWidth("40px");
		pricePanel.setWidget(_price);
		
		_quantitiy=new MorphWidget(_productData.getQuantity().getQuantity()+"", Datatype.INT, _editable);
		_quantitiy.setWidth("40px");
		quantitiyPanel.setWidget(_quantitiy);
		
		currency.setText(_productData.getAvgPrice().getCurrency().getTitle());
		unit.setText(_productData.getQuantity().getUnit().getTitle());
		
		if(_editable && !_productData.hasReceipt())
			noReceiptName();
		
		
		
		//TODO Just for a Test
		progressImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				History.newItem("product/get&id="+_productData.getId());					
			}
		});
	}
	

	@Override
	public void click(){
		//TODO Don't call if click is on ratingWidget
		//History.newItem("product/get&id="+productData.getId());		
	}
	

	
	/**
	 * 
	 */
	private void noReceiptName(){
		final VerticalPanel VePa1 = new VerticalPanel();
		VePa1.setWidth("100%");
		VePa1.setStyleName("Warning");
		_receipt=new MorphWidget("Insert here missing receipt name!", Datatype.STRING,  _editable);
		_receipt.setWidth("100%");
		VePa1.add(_receipt);
		this.VePa1.add(VePa1);
		

	}

	/**
	 * Return current productData
	 * @return 
	 */
	public ProductData getProductData(){
		if(_editable){
			_productData.getAvgPrice().setPrice((int)(Double.parseDouble(_price.getValue())*100));
			_productData.getQuantity().setQuantity(new Integer(_quantitiy.getValue()));
			_productData.setRating(_ratingWidget.getRating());
		}
		
		return _productData;
	}
	
	/**
	 * 
	 * @return Is ProductPreview editable
	 */
	public boolean isEditable(){
		return _editable;
	}

	
	/**
	 * Handler is called when price change. 
	 * @param handler
	 */
	public void addChangeHandler(ChangeHandler handler){
		_price.addChangeHandler(handler);
	}
	
	
}

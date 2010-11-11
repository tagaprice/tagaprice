/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: SearchWidget2.java
 * Date: 20.07.2010
*/
package org.tagaprice.client.widgets;

import java.util.ArrayList;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.pages.previews.ProductPagePreview;
import org.tagaprice.client.pages.previews.ShopPagePreview;
import org.tagaprice.client.widgets.SelectiveListWidget.SelectionType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ShopData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SearchWidget extends Composite {

	public enum SearchType implements Serializable {
		ALL, PRODCUT, SHOP;

		@Override
		public String getSerializeName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	private TextBox searchBox = new TextBox();
	private VerticalPanel vePa1 = new VerticalPanel();
	private VerticalPanel vePa2 = new VerticalPanel();
	private SelectiveListWidget selVePa;
	private PopupPanel popPa;
	TaPManager myMng = TaPManager.getInstance();
	
	//Constructor Values
	private SearchType _searchType;
	private boolean _showNew;
	private boolean _popup;
	private SelectionType _selectionType;
	private BoundingBox _bbox=null;
	private ShopData _shopData=null;
	private long myCurRequest = 0;
	
	public SearchWidget(
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType) {
		
		init(searchType, showNew, popup, selectionType);	
		
		searchBox.setStyleName("searchBox");
		//Search	
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				myCurRequest++;
				
				if(searchBox.getText().isEmpty()){
					selVePa.clear();
				}else{
					newStdRequest(myCurRequest);

				}
			}
		});

		
	}
	
	
	public SearchWidget(
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType,
			BoundingBox bbox) {
		_bbox=bbox;
		init(searchType, showNew, popup, selectionType);	
		
		
		//Search		
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				myCurRequest++;
				
				if(searchBox.getText().isEmpty()){
					selVePa.clear();
				}else{
					newBboxRequest(myCurRequest);
				}
			}
		});
		
		
	}
	
	public SearchWidget(
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType,
			ShopData shopData) {
		_shopData=shopData;
		init(searchType, showNew, popup, selectionType);	
		
		//Search				
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				myCurRequest++;
				
				if(searchBox.getText().isEmpty()){
					selVePa.clear();
				}else{
					newShopRequest(myCurRequest);
				}
			}
		});
	}
	
	
	private void init(
			SearchType searchType,
			boolean showNew, 
			boolean popup,
			SelectionType selectionType){
		//Constructor Values
		_searchType=searchType;
		_showNew=showNew;
		_popup=popup;
		_selectionType=selectionType;
		
		
		
		initWidget(vePa1);
		vePa1.setWidth("100%");
		vePa1.add(searchBox);
		searchBox.setWidth("100%");
		vePa2.setWidth("100%");
		
		//
		selVePa = new SelectiveListWidget(_selectionType);
		selVePa.setWidth("100%");
		
		//popup
		if(_popup){
			popPa=new PopupPanel(true);
			popPa.setWidget(vePa2);
			popPa.setWidth("100%");
		}else{
			vePa1.add(vePa2);
		}
		
		
		vePa2.add(selVePa);
		
		if(_showNew){
			boolean product = false;
			boolean shop = false;
			if(_searchType.equals(SearchType.PRODCUT)){
				product=true;
			}else if(_searchType.equals(SearchType.SHOP)){
				shop=true;
			}else{
				product=true;
				shop=true;
			}
			
			if(product){
				Label productLabel = new Label("New Product");
				vePa2.add(productLabel);
				
				productLabel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						History.newItem("product/new");
					}
				});
			}
			
			if(shop){
				Label shopLabel = new Label("New Shop");
				vePa2.add(shopLabel);
				
				shopLabel.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						History.newItem("shop/new");
					}
				});
			}
		}
		
		
		searchBox.addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				if(popPa!=null && !searchBox.getText().isEmpty())
					popPa.showRelativeTo(searchBox);
			}
		});
	}
	
	public SelectiveListWidget getSelectiveVerticalPanel(){
		return selVePa;
	}
	
	public void hideSuggest(){
		popPa.hide();
	}
	
	private void newShopRequest(final long curReq){
		RPCHandlerManager.getSearchHandler().search(searchBox.getText(), _shopData, new AsyncCallback<ArrayList<Entity>>() {
			
			@Override
			public void onSuccess(ArrayList<Entity> result) {
				if(curReq==myCurRequest){
					selVePa.clear();
					if(_popup)
						popPa.showRelativeTo(searchBox);
					
					for(Entity sResult:result){
						if(sResult instanceof ProductData){
							selVePa.add(new ProductPagePreview((ProductData)sResult, false));
						}else if(sResult instanceof ShopData){
							selVePa.add(new ShopPagePreview((ShopData)sResult, false));
						}
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("error at searching");
				
			}
		});		
	}
	
	private void newBboxRequest(final long curReq){
		RPCHandlerManager.getSearchHandler().search(searchBox.getText(), _searchType, _bbox,  new AsyncCallback<ArrayList<Entity>>() {
			
			@Override
			public void onSuccess(ArrayList<Entity> result) {
				if(curReq==myCurRequest){
					selVePa.clear();
					if(_popup)
						popPa.showRelativeTo(searchBox);
					
					for(Entity sResult:result){
						if(sResult instanceof ProductData){
							selVePa.add(new ProductPagePreview((ProductData)sResult, false));
						}else if(sResult instanceof ShopData){
							selVePa.add(new ShopPagePreview((ShopData)sResult, false));
						}
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("error at searching");
				
			}
		});		
	}
	
	private void newStdRequest(final long curReq){		
		RPCHandlerManager.getSearchHandler().search(searchBox.getText(), _searchType, new AsyncCallback<ArrayList<Entity>>() {
			
			@Override
			public void onSuccess(ArrayList<Entity> result) {
				if(curReq==myCurRequest){				
					selVePa.clear();
					if(_popup)
						popPa.showRelativeTo(searchBox);
					
					for(Entity sResult:result){							
						//selVePa.add(new Label(sResult.getTitle()));
						
						
						if(sResult instanceof ProductData){
							selVePa.add(new ProductPagePreview((ProductData)sResult, false));
						}else if(sResult instanceof ShopData){
							selVePa.add(new ShopPagePreview((ShopData)sResult, false));
						}
						
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("error at searching");
				
			}
		});		
	}
}

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
 * Filename: ProductWidget.java
 * Date: 19.05.2010
 */
package org.tagaprice.client.pages;

import java.util.ArrayList;
import java.util.HashMap;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.ImageBundle;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.propertyhandler.DefaultPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyHandler;
import org.tagaprice.client.propertyhandler.ListPropertyHandler;
import org.tagaprice.client.propertyhandler.IPropertyChangeHandler;
import org.tagaprice.client.widgets.InfoBoxWidget;
import org.tagaprice.client.widgets.MorphWidget;
import org.tagaprice.client.widgets.IMorphWidgetInfoHandler;
import org.tagaprice.client.widgets.PriceMapWidget;
import org.tagaprice.client.widgets.ProgressWidget;
import org.tagaprice.client.widgets.RatingWidget;
import org.tagaprice.client.widgets.TypeWidget;
import org.tagaprice.client.widgets.ITypeWidgetHandler;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyGroup;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.Type;
import org.tagaprice.shared.PropertyDefinition.Datatype;
import org.tagaprice.shared.exception.InvalidLoginException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductPage extends APage {

	private ProductData _productData;
	private HashMap<String, ArrayList<PropertyData>> _hashProperties = new HashMap<String, ArrayList<PropertyData>>();
	private Type _type;
	private VerticalPanel _verticalPanel_1 = new VerticalPanel();
	private IPropertyChangeHandler _handler;
	private ArrayList<IPropertyHandler> _handlerList = new ArrayList<IPropertyHandler>();
	private InfoBoxWidget __bottomInfo = new InfoBoxWidget(false);
	private PriceMapWidget _priceMap;
	private SimplePanel _typeWidgetContainer = new SimplePanel();
	private SimplePanel _propertyHandlerContainer = new SimplePanel();
	private MorphWidget _titleMorph = new MorphWidget("", Datatype.STRING, true);

	/**
	 * The Constructor creates a ProductPage for a product
	 * 
	 * @param productData
	 * @param _type
	 */
	public ProductPage(ProductData productData, Type _type) {

		init(this._verticalPanel_1);
		this._productData = productData;

		this._type = _type;
		// Move PropertyData to hashPropertyData
		for (PropertyData pd : this._productData.getProperties()) {
			if (_hashProperties.get(pd.getName()) == null) {
				_hashProperties
						.put(pd.getName(), new ArrayList<PropertyData>());
			}
			_hashProperties.get(pd.getName()).add(pd);

		}

		// style
		_verticalPanel_1.setWidth("100%");

		// Header
		HorizontalPanel hoPa1 = new HorizontalPanel();
		hoPa1.setWidth("100%");
		_verticalPanel_1.add(_titleMorph);
		_titleMorph.setText(_productData.getTitle());
		_titleMorph.setWidth("100%");
		_verticalPanel_1.add(hoPa1);
		ProgressWidget progressWidget = new ProgressWidget(new Image(
				ImageBundle.INSTANCE.productPriview()),
				_productData.getProgress());
		hoPa1.add(progressWidget);

		VerticalPanel vePa2 = new VerticalPanel();
		vePa2.setWidth("100%");
		hoPa1.add(vePa2);
		hoPa1.setCellWidth(vePa2, "100%");

		// Type
		vePa2.add(_typeWidgetContainer);
		drawTypeWidget();

		// Rating
		vePa2.add(new RatingWidget(this._productData.getRating(), false));

		// Listener
		_handler = new IPropertyChangeHandler() {

			@Override
			public void onSuccess() {
				showSave();
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}
		};

		_titleMorph.addMorphWidgetInfoHandler(new IMorphWidgetInfoHandler() {

			@Override
			public void onSuccess(Datatype errorType) {

				if (!_productData.getTitle().equals(_titleMorph.getValue())) {
					_productData.setTitle(_titleMorph.getValue());
					showSave();

				}
			}

			@Override
			public void onError(Datatype errorType) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onEmpty() {
				// TODO Auto-generated method stub
			}
		});

		// Add Price
		if (_productData.getId() != null) {
			final SimplePanel priceMapContaier = new SimplePanel();
			priceMapContaier.setWidth("100%");
			_verticalPanel_1.add(priceMapContaier);
			GWT.runAsync(new RunAsyncCallback() {

				@Override
				public void onSuccess() {
					_priceMap = new PriceMapWidget(_productData.getId(),
							PriceMapType.PRODUCT);
					priceMapContaier.setWidget(_priceMap);
				}

				@Override
				public void onFailure(Throwable reason) {
					showInfo("Download Error at PriceWidget",
							BoxType.WARNINGBOX);

				}
			});

		}

		// Create and Register Handler
		_verticalPanel_1.add(_propertyHandlerContainer);
		_propertyHandlerContainer.setWidth("100%");
		registerHandler();

		_verticalPanel_1.add(__bottomInfo);
	}

	private void drawTypeWidget() {
		_typeWidgetContainer.setWidget(new TypeWidget(_type,
				new ITypeWidgetHandler() {
					@Override
					public void onChange(Type newType) {

						// Get type and set type
						RPCHandlerManager.getTypeHandler().get(newType,
								new AsyncCallback<Type>() {
									@Override
									public void onFailure(Throwable caught) {
										showInfo("ProductPage getTypeError",
												BoxType.WARNINGBOX);
									}

									@Override
									public void onSuccess(Type result) {
										_type = result;
										drawTypeWidget();
										registerHandler();
										showSave();
									}

								});

					}
				}));
	}

	private void registerHandler() {
		_handlerList.clear();

		for (String ks : _hashProperties.keySet()) {
			for (PropertyData pd : _hashProperties.get(ks)) {
				pd.setRead(false);
			}
		}

		VerticalPanel hVePa = new VerticalPanel();
		hVePa.setWidth("100%");

		// Add Properties
		for (PropertyGroup pg : this._type.getPropertyGroups()) {

			if (pg.getType().equals(PropertyGroup.GroupType.LIST)) {
				ListPropertyHandler temp = new ListPropertyHandler(
						_hashProperties, pg, _handler);
				_handlerList.add(temp);
				hVePa.add(temp);

			}

		}

		DefaultPropertyHandler defH = new DefaultPropertyHandler(
				_hashProperties, _handler);
		_handlerList.add(defH);
		hVePa.add(defH);

		_propertyHandlerContainer.setWidget(hVePa);
	}


	private void showSave() {
		if (TaPManager.getInstance().isLoggedIn() != null) {
			showSaveLogin();

		} else {
			showSaveNotLogin();
		}

	}

	private void showSaveLogin() {
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		Button topAbort = new Button("Abort", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (_productData.getId() == null) {
					History.newItem("home/");
				} else {
					TaPManager.getInstance().showProductPage(
							_productData.getId());
				}

			}
		});
		final Button topSave = new Button("Save");
		topSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				topSave.setText("Saving...");

				_productData.setProperties(hashToPropertyList(_hashProperties));
				_productData.setTypeId(new Long(_type.getId()));

				// Validate Data
				if (PropertyValidator.isValid(_type,
						_productData.getProperties())) {
					try {
						RPCHandlerManager.getProductHandler().save(
								_productData, new AsyncCallback<ProductData>() {

									@Override
									public void onSuccess(ProductData result) {
										__bottomInfo.setVisible(false);
										topSave.setText("Save");
										if (_productData.getId() == null) {
											History.newItem("product/get&id="
													+ result.getId());
										} else {
											_productData = result;
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										showInfo("SaveFail: " + caught,
												BoxType.WARNINGBOX);

									}
								});
					} catch (IllegalArgumentException e) {
						showInfo("IllegalArgumentException: " + e,
								BoxType.WARNINGBOX);

					} catch (InvalidLoginException e) {
						showInfo("InvalidLoginException: " + e,
								BoxType.WARNINGBOX);
					}
				} else {
					showInfo("SaveFail: Invalide Data", BoxType.WARNINGBOX);
				}

			}
		});
		bottomInfoHoPa.add(topAbort);
		bottomInfoHoPa.add(topSave);
		bottomInfoHoPa.setCellWidth(topAbort, "100%");
		bottomInfoHoPa.setCellHorizontalAlignment(topAbort,
				HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellHorizontalAlignment(topSave,
				HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellVerticalAlignment(topAbort,
				HasVerticalAlignment.ALIGN_MIDDLE);
		bottomInfoHoPa.setCellVerticalAlignment(topSave,
				HasVerticalAlignment.ALIGN_MIDDLE);
		__bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}

	private void showSaveNotLogin() {
		HorizontalPanel bottomInfoHoPa = new HorizontalPanel();
		bottomInfoHoPa.setWidth("100%");
		// bottomInfoHoPa.setHeight("100%");
		Button topAbort = new Button("Abort", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (_productData.getId() == null) {
					History.newItem("home/");
				} else {
					TaPManager.getInstance().showProductPage(
							_productData.getId());
				}

			}
		});
		Label pleaseLoginLabel = new Label("Please login for saving!");
		bottomInfoHoPa.add(pleaseLoginLabel);
		bottomInfoHoPa.add(topAbort);
		bottomInfoHoPa.setCellWidth(pleaseLoginLabel, "100%");
		bottomInfoHoPa.setCellHorizontalAlignment(topAbort,
				HasHorizontalAlignment.ALIGN_RIGHT);
		bottomInfoHoPa.setCellVerticalAlignment(topAbort,
				HasVerticalAlignment.ALIGN_MIDDLE);
		__bottomInfo.showInfo(bottomInfoHoPa, BoxType.WARNINGBOX);
	}

	/**
	 * 
	 * @param hashProperties
	 * @return
	 */
	private SearchResult<PropertyData> hashToPropertyList(
			HashMap<String, ArrayList<PropertyData>> hashProperties) {
		SearchResult<PropertyData> newList = new SearchResult<PropertyData>();

		for (String ks : hashProperties.keySet()) {
			for (PropertyData pd : hashProperties.get(ks)) {
				newList.add(pd);
				// System.out.println(pd.getName()+": "+pd.getValue());
			}
		}
		return newList;
	}

	/**
	 * Set the position
	 */
	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		_priceMap.setAddress(address);
	}

}

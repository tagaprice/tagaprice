package org.tagaprice.client.widgets;

import java.util.ArrayList;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.TaPManager;
import org.tagaprice.client.pages.IAddressHandler;
import org.tagaprice.client.widgets.TitleWidget.Headline;
import org.tagaprice.shared.ISerializable;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.PriceData;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.HierarchicalMapTypeControl;
import com.google.gwt.maps.client.control.SmallZoomControl3D;
import com.google.gwt.maps.client.event.MapDragEndHandler;
import com.google.gwt.maps.client.event.MapZoomEndHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A PriceMapWidget displays a price table and a map with shops, prices, date
 * ,... depending on the PriceMapType
 * 
 */
public class PriceMapWidget extends Composite implements IAddressHandler {

	public enum PriceMapType implements ISerializable {

		/**
		 * PRODUCT (id: must be a Product_id) displays a map with shops in which
		 * you can find this product plus a price table.
		 */
		PRODUCT,

		/**
		 * PRODUCTGROUP (id: must be a product_group_id (e.g. Milk id)) Shows a
		 * map with shops in which you can find this product-types plus a price
		 * table.
		 */
		CATEGORY,

		/**
		 * SHOP (id: must be a shop_id) Shows a grid with all products you can
		 * find in a shop plus a price table.
		 */
		SHOP,

		/**
		 * SHOPGROUP (id: must be a shop_group_id (e.g. Billa id)) Shows a map with all
		 * shops of shop_group. (Maybe this should be changed to Brand-Group)
		 */
		BRANDING; 

		public String getSerializeName() {
			return "priceMapType";
		}
	}

	private long _id;
	private MapWidget _map;
	private FlexTable _priceTable = new FlexTable();
	private TaPManager _TaPM = TaPManager.getInstance();
	private TitleWidget _titlePanel;
	private PriceMapType _type;
	private VerticalPanel _vePa1 = new VerticalPanel();

	/**
	 * Creates a PriceMapWidget with an entity id (only: product, shop, branding, category).
	 * @param id is a product, shop, branding, or category id. Must not be NULL.
	 * @param priceMapType defines the displayed type
	 */
	public PriceMapWidget(long id, PriceMapType priceMapType) {
		_type = priceMapType;
		this._id = id;
		_vePa1.setWidth("100%");
		_priceTable.setWidth("100%");
		_titlePanel = new TitleWidget("Pricetable", _vePa1, Headline.H2);

		if (_type.equals(PriceMapType.PRODUCT)
				|| _type.equals(PriceMapType.CATEGORY)
				|| _type.equals(PriceMapType.BRANDING)) {
			_map = new MapWidget(LatLng.newInstance(_TaPM.getCurrentAddress()
					.getLat(), _TaPM.getCurrentAddress().getLng()), 16);
			_map.addControl(new SmallZoomControl3D());
			_map.addControl(new HierarchicalMapTypeControl());

			getPrices();

			_map.setWidth("100%");
			_map.setHeight("200px");
			_vePa1.add(_map);

			// MapMoveListen
			_map.addMapDragEndHandler(new MapDragEndHandler() {

				@Override
				public void onDragEnd(MapDragEndEvent event) {
					getPrices();
				}
			});

			_map.addMapZoomEndHandler(new MapZoomEndHandler() {
				@Override
				public void onZoomEnd(MapZoomEndEvent event) {
					getPrices();
				}
			});
		} else if (_type.equals(PriceMapType.SHOP)) {
			getPrices();
		}

		_vePa1.add(_priceTable);

		initWidget(_titlePanel);
	}

	
	@Override
	public void setAddress(Address address) {
		System.out.println("setAdress");
		if (_map != null)
			_map.setCenter(LatLng.newInstance(address.getLat(), address.getLng()));
	}

	
	/**
	 * Gets the prices from the db at the current position
	 */
	private void getPrices() {
		if (_type.equals(PriceMapType.SHOP)) {
			RPCHandlerManager.getPriceHandler().get(_id, null, _type,
					new AsyncCallback<ArrayList<PriceData>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println("Price Load Error");
						}

						@Override
						public void onSuccess(ArrayList<PriceData> result) {
							refreshData(result);
						}
					});
		} else {
			// Get Prices
			RPCHandlerManager.getPriceHandler().get(
					_id,
					new BoundingBox(_map.getBounds().getSouthWest()
							.getLatitude(), _map.getBounds().getSouthWest()
							.getLongitude(), _map.getBounds().getNorthEast()
							.getLatitude(), _map.getBounds().getNorthEast()
							.getLongitude()), _type,
					new AsyncCallback<ArrayList<PriceData>>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println("Price Load Error");
						}

						@Override
						public void onSuccess(ArrayList<PriceData> result) {
							refreshData(result);
						}

					});
		}
	}

	/**
	 * Refreshes the price table
	 * @param list a list of the prices at this position
	 */
	private void refreshData(ArrayList<PriceData> list) {
		int pinOff = 1;
		int colOff = 0;

		// CreateTable
		_priceTable.removeAllRows();

		// Set Header

		if (_type.equals(PriceMapType.SHOP)) {
			_priceTable.setText(0, 0, "Pin");
			pinOff = 1;
			_priceTable.setText(0, 0 + pinOff, "Products");
			colOff = 0;
		} else if (_type.equals(PriceMapType.PRODUCT)) {
			pinOff = 0;
			_priceTable.setText(0, 0 + pinOff, "Shops");
			colOff = 0;
		} else {
			_priceTable.setText(0, 0, "Ping");
			pinOff = 1;
			_priceTable.setText(0, 0 + pinOff, "Product");
			_priceTable.setText(0, 1 + pinOff, "Shops");
			colOff = 1;
		}

		_priceTable.setText(0, 2 + pinOff + colOff, "Quality");
		_priceTable.setText(0, 3 + pinOff + colOff, "Date");
		_priceTable.setText(0, 4 + pinOff + colOff, "Price");

		// Create Table
		if (_map != null)
			_map.clearOverlays();

		int row = 1;
		for (final PriceData pd : list) {

			_priceTable.setText(row, 0, "*");

			if (_type.equals(PriceMapType.SHOP)) {
				Label tempProductL = new Label(pd.getProductData().getTitle());
				_priceTable.setWidget(row, 0 + pinOff, tempProductL);
				tempProductL.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						History.newItem("product/get&id="
								+ pd.getProductData().getId());
					}
				});
			} else {
				Label tempShopL = new Label(pd.getShopData().getTitle());
				_priceTable.setWidget(row, 0 + pinOff, tempShopL);
				tempShopL.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						History.newItem("shop/get&id="
								+ pd.getShopData().getId());
					}
				});
			}
			_priceTable.setWidget(row, 2 + pinOff + colOff, new RatingWidget(pd
					.getProductData().getRating(), false));
			_priceTable.setText(row, 3 + pinOff + colOff, DateTimeFormat
					.getLongDateFormat().format(pd.getDate()));
			_priceTable.setText(row, 4 + pinOff + colOff, ""
					+ (pd.getPrice().getPrice() / 100.00) + ""
					+ pd.getPrice().getCurrency().getTitle());

			if (!_type.equals(PriceMapType.SHOP))
				_map.addOverlay(new Marker(LatLng.newInstance(pd.getShopData()
						.getAddress().getLat(), pd.getShopData().getAddress()
						.getLng())));

			row++;
		}
	}
}

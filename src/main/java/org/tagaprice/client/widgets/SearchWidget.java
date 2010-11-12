package org.tagaprice.client.widgets;

import java.util.ArrayList;
import org.tagaprice.client.RPCHandlerManager;
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

/**
 * The SearchWidget is a special TextBox with a suggestion list. The search area
 * can be set to all, shop or product. If the SelectionType is set to
 * MINUSBUTTON or PLUSBUTTON, you see a plus/minus button in the suggestion
 * list.
 * 
 */
public class SearchWidget extends Composite {

	public enum SearchType implements Serializable {

		/**
		 * Search for everything in the db.
		 */
		ALL,
		/**
		 * Will only search for products
		 */
		PRODCUT,

		/**
		 * Will only search for shops
		 */
		SHOP;

		@Override
		public String getSerializeName() {
			return "org.tagaprice.client.widgets.SearchWidget";
		}
	}

	private BoundingBox _bbox = null;
	private boolean _popup;
	private SearchType _searchType;
	private SelectionType _selectionType;
	private ShopData _shopData = null;

	private boolean _showNew;
	private long _myCurRequest = 0;
	private PopupPanel _popPa;
	private TextBox _searchBox = new TextBox();
	private SelectiveListWidget _selVePa;
	private VerticalPanel _vePa1 = new VerticalPanel();
	private VerticalPanel _vePa2 = new VerticalPanel();

	/**
	 * Create a SearchWidget that will search the whole db.
	 * 
	 * @param searchType
	 *            defines the search result type.
	 * @param showNew
	 *            if TRUE a button to create a new shop/product will be
	 *            displayed
	 * @param popup
	 *            displays the search results in a popup panel or in a vertical
	 *            list
	 * @param selectionType
	 *            defines the select able button. (NO,PLUS,MINUS)
	 */
	public SearchWidget(SearchType searchType, boolean showNew, boolean popup,
			SelectionType selectionType) {

		init(searchType, showNew, popup, selectionType);

		_searchBox.setStyleName("searchBox");
		// Search
		_searchBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				_myCurRequest++;

				if (_searchBox.getText().isEmpty()) {
					_selVePa.clear();
				} else {
					newStdRequest(_myCurRequest);

				}
			}
		});

	}

	/**
	 * Create a SearchWidget that will search the whole db in a specific area.
	 * 
	 * @param searchType
	 *            defines the search result type.
	 * @param showNew
	 *            if TRUE a button to create a new shop/product will be
	 *            displayed
	 * @param popup
	 *            displays the search results in a popup panel or in a vertical
	 *            list
	 * @param selectionType
	 * 
	 * @param bbox
	 *            defines a bounding box in which the db should search.
	 */
	public SearchWidget(SearchType searchType, boolean showNew, boolean popup,
			SelectionType selectionType, BoundingBox bbox) {
		_bbox = bbox;
		init(searchType, showNew, popup, selectionType);

		// Search
		_searchBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				_myCurRequest++;

				if (_searchBox.getText().isEmpty()) {
					_selVePa.clear();
				} else {
					newBboxRequest(_myCurRequest);
				}
			}
		});

	}

	
	/**
	 * dsd
	 * @param searchType
	 * @param showNew
	 * @param popup
	 * @param selectionType
	 * @param shopData
	 */
	public SearchWidget(SearchType searchType, boolean showNew, boolean popup,
			SelectionType selectionType, ShopData shopData) {
		_shopData = shopData;
		init(searchType, showNew, popup, selectionType);

		// Search
		_searchBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				_myCurRequest++;

				if (_searchBox.getText().isEmpty()) {
					_selVePa.clear();
				} else {
					newShopRequest(_myCurRequest);
				}
			}
		});
	}

	public SelectiveListWidget getSelectiveVerticalPanel() {
		return _selVePa;
	}

	public void hideSuggest() {
		_popPa.hide();
	}

	private void init(SearchType searchType, boolean showNew, boolean popup,
			SelectionType selectionType) {
		// Constructor Values
		_searchType = searchType;
		_showNew = showNew;
		_popup = popup;
		_selectionType = selectionType;

		initWidget(_vePa1);
		_vePa1.setWidth("100%");
		_vePa1.add(_searchBox);
		_searchBox.setWidth("100%");
		_vePa2.setWidth("100%");

		//
		_selVePa = new SelectiveListWidget(_selectionType);
		_selVePa.setWidth("100%");

		// popup
		if (_popup) {
			_popPa = new PopupPanel(true);
			_popPa.setWidget(_vePa2);
			_popPa.setWidth("100%");
		} else {
			_vePa1.add(_vePa2);
		}

		_vePa2.add(_selVePa);

		if (_showNew) {
			boolean product = false;
			boolean shop = false;
			if (_searchType.equals(SearchType.PRODCUT)) {
				product = true;
			} else if (_searchType.equals(SearchType.SHOP)) {
				shop = true;
			} else {
				product = true;
				shop = true;
			}

			if (product) {
				Label productLabel = new Label("New Product");
				_vePa2.add(productLabel);

				productLabel.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						History.newItem("product/new");
					}
				});
			}

			if (shop) {
				Label shopLabel = new Label("New Shop");
				_vePa2.add(shopLabel);

				shopLabel.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						History.newItem("shop/new");
					}
				});
			}
		}

		_searchBox.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (_popPa != null && !_searchBox.getText().isEmpty())
					_popPa.showRelativeTo(_searchBox);
			}
		});
	}

	private void newBboxRequest(final long curReq) {
		RPCHandlerManager.getSearchHandler().search(_searchBox.getText(),
				_searchType, _bbox, new AsyncCallback<ArrayList<Entity>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("error at searching");

					}

					@Override
					public void onSuccess(ArrayList<Entity> result) {
						if (curReq == _myCurRequest) {
							_selVePa.clear();
							if (_popup)
								_popPa.showRelativeTo(_searchBox);

							for (Entity sResult : result) {
								if (sResult instanceof ProductData) {
									_selVePa.add(new ProductPagePreview(
											(ProductData) sResult, false));
								} else if (sResult instanceof ShopData) {
									_selVePa.add(new ShopPagePreview(
											(ShopData) sResult, false));
								}
							}
						}
					}
				});
	}

	private void newShopRequest(final long curReq) {
		RPCHandlerManager.getSearchHandler().search(_searchBox.getText(),
				_shopData, new AsyncCallback<ArrayList<Entity>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						System.out.println("error at searching");

					}

					@Override
					public void onSuccess(ArrayList<Entity> result) {
						if (curReq == _myCurRequest) {
							_selVePa.clear();
							if (_popup)
								_popPa.showRelativeTo(_searchBox);

							for (Entity sResult : result) {
								if (sResult instanceof ProductData) {
									_selVePa.add(new ProductPagePreview(
											(ProductData) sResult, false));
								} else if (sResult instanceof ShopData) {
									_selVePa.add(new ShopPagePreview(
											(ShopData) sResult, false));
								}
							}
						}
					}
				});
	}

	private void newStdRequest(final long curReq) {
		RPCHandlerManager.getSearchHandler().search(_searchBox.getText(),
				_searchType, new AsyncCallback<ArrayList<Entity>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("error at searching");

					}

					@Override
					public void onSuccess(ArrayList<Entity> result) {
						if (curReq == _myCurRequest) {
							_selVePa.clear();
							if (_popup)
								_popPa.showRelativeTo(_searchBox);

							for (Entity sResult : result) {
								// selVePa.add(new Label(sResult.getTitle()));

								if (sResult instanceof ProductData) {
									_selVePa.add(new ProductPagePreview(
											(ProductData) sResult, false));
								} else if (sResult instanceof ShopData) {
									_selVePa.add(new ShopPagePreview(
											(ShopData) sResult, false));
								}

							}
						}
					}
				});
	}
}

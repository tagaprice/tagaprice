package org.tagaprice.client.features.shopmanagement.listShops.devView;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.generics.ColumnDefinition;
import org.tagaprice.shared.logging.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The Class ListProductsViewImpl<T> uses UIBinder and the template ListProductsViewImpl.ui.xml
 * 
 * 
 * @param <T>
 */
public class ListShopsViewImpl<T> extends Composite implements ListShopsView<T> {

	Presenter presenter;

	private static MyLogger logger = LoggerFactory.getLogger(ListShopsViewImpl.class);

	/**
	 * UiBinder Magic...
	 * 
	 * @author Martin
	 * 
	 */
	@SuppressWarnings("rawtypes")
	interface ListProductsViewImplUiBinder extends UiBinder<Widget, ListShopsViewImpl> {
	}

	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * UiBinder Magic again...
	 */
	private static ListProductsViewImplUiBinder uiBinder = GWT.create(ListProductsViewImplUiBinder.class);

	/**
	 * and again...
	 */
	@UiField
	FlexTable table;

	@UiField
	Button addProduct;

	@UiField
	TextBox textbox;

	public FlexTable getTable() {
		return this.table;
	}

	@UiHandler("search")
	public void onSearchButtonClicked(ClickEvent event) {
		ListShopsViewImpl.logger.log("Search Button clicked");
		this.presenter.onSearch(this.textbox.getText());
	}

	@UiHandler("addProduct")
	public void onAddProductButtonClicked(ClickEvent event) {
		ListShopsViewImpl.logger.log("Button addProduct clicked");
		this.presenter.onAddShop();
	}

	@UiHandler("table")
	public void onTableEntryClicked(ClickEvent event) {
		ListShopsViewImpl.logger.log("Entry on Table clicked");
		this.presenter.onEditShop(this.table.getCellForEvent(event).getRowIndex());
	}

	ArrayList<ColumnDefinition<T>> columnDefinitions;

	public ListShopsViewImpl() {
		super();
		this.initWidget(ListShopsViewImpl.uiBinder.createAndBindUi(this));
	}

	/**
	 * Sets the ColumnDefinitions
	 * 
	 * @param columnDefinitions
	 */
	public void setColumnDefinitions(ArrayList<ColumnDefinition<T>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	@Override
	public void setData(List<T> data) {
		this.table.removeAllRows();
		for (int i = 0; i < data.size(); i++) {
			T elem = data.get(i);
			for (int j = 0; j < this.columnDefinitions.size(); j++) {
				ColumnDefinition<T> actualColumnDefinition = this.columnDefinitions.get(j);
				this.table.setWidget(i, j, actualColumnDefinition.render(elem));
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
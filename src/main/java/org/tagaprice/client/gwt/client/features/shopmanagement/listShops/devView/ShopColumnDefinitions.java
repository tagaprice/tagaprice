package org.tagaprice.client.gwt.client.features.shopmanagement.listShops.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.ColumnDefinition;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

public class ShopColumnDefinitions {

	ArrayList<ColumnDefinition<IShop>> columns = new ArrayList<ColumnDefinition<IShop>>();

	public ShopColumnDefinitions() {

	}

	public ArrayList<ColumnDefinition<IShop>> getColumnDefinitions() {
		return this.columns;
	}
}

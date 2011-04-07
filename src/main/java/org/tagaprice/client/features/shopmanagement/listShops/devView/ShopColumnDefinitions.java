package org.tagaprice.client.features.shopmanagement.listShops.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.ColumnDefinition;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.user.client.ui.*;

public class ShopColumnDefinitions {

	ArrayList<ColumnDefinition<Shop>> columns = new ArrayList<ColumnDefinition<Shop>>();

	public ShopColumnDefinitions() {
		columns.add(new ColumnDefinition<Shop>() {

			@Override
			public Widget render(Shop t) {
				return new HTML(t.getTitle());
			}

			@Override
			public String getColumnName() {
				return "Title";
			}
		});
		this.columns.add(new ColumnDefinition<Shop>() {

			@Override
			public Widget render(Shop t) {

				return new Anchor("Open/Edit", "#CreateShop:/show/id/" + t.getId());

			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "Action";
			}
		});
		this.columns.add(new ColumnDefinition<Shop>() {

			@Override
			public Widget render(Shop t) {
				if(t.getId() != null)
					return new HTML(t.getId());
				else
					return new HTML("Revision is null");
			}

			@Override
			public String getColumnName() {
				return "Delete";
			}
		});

	}

	public ArrayList<ColumnDefinition<Shop>> getColumnDefinitions() {
		return this.columns;
	}
}

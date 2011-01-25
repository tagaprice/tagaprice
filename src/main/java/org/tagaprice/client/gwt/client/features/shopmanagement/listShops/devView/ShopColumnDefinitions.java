package org.tagaprice.client.gwt.client.features.shopmanagement.listShops.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.ColumnDefinition;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.ui.*;

public class ShopColumnDefinitions {

	ArrayList<ColumnDefinition<IShop>> columns = new ArrayList<ColumnDefinition<IShop>>();

	public ShopColumnDefinitions() {
		columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {
				return new HTML(t.getTitle());
			}

			@Override
			public String getColumnName() {
				return "Title";
			}
		});
		this.columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {

				return new Anchor("Open/Edit", "#CreateShop:/show/id/" + t.getRevisionId().getId());

			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "Action";
			}
		});
		this.columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {
				if(t.getRevisionId() != null)
					return new HTML(t.getRevisionId().toString());
				else
					return new HTML("Revision is null");
			}

			@Override
			public String getColumnName() {
				return "Delete";
			}
		});

	}

	public ArrayList<ColumnDefinition<IShop>> getColumnDefinitions() {
		return this.columns;
	}
}

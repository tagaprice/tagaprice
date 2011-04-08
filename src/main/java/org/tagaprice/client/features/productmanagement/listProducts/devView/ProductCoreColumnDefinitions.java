package org.tagaprice.client.features.productmanagement.listProducts.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.ColumnDefinition;
import org.tagaprice.shared.entities.productmanagement.Product;



import com.google.gwt.user.client.ui.*;

public class ProductCoreColumnDefinitions {

	ArrayList<ColumnDefinition<Product>> columns = new ArrayList<ColumnDefinition<Product>>();

	public ProductCoreColumnDefinitions() {
		this.columns.add(new ColumnDefinition<Product>() {

			@Override
			public Widget render(Product t) {
				return new HTML(t.getTitle());
			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "name";
			}
		});
		this.columns.add(new ColumnDefinition<Product>() {

			@Override
			public Widget render(Product t) {
				if (t.getCategory() == null) return new HTML("[no category]"); 
				return new HTML(t.getCategory().toString());
			}

			@Override
			public String getColumnName() {
				return "Category";
			}
		});
		this.columns.add(new ColumnDefinition<Product>() {

			@Override
			public Widget render(Product t) {
				return new HTML(t.getUnit().toString());
			}

			@Override
			public String getColumnName() {
				return "Selling Unit";
			}
		});
		this.columns.add(new ColumnDefinition<Product>() {

			@Override
			public Widget render(Product t) {

				return new Anchor("Open/Edit", "#CreateProduct:/show/id/" + t.getId());

			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "Action";
			}
		});

		this.columns.add(new ColumnDefinition<Product>() {

			@Override
			public Widget render(Product t) {
				if(t.getId() != null)
					return new HTML("id: "+t.getId()+", rev: "+t.getRevision());
				else
					return new HTML("Revision is null");
			}

			@Override
			public String getColumnName() {
				return "Delete";
			}
		});
	}

	public ArrayList<ColumnDefinition<Product>> getColumnDefinitions() {
		return this.columns;
	}
}

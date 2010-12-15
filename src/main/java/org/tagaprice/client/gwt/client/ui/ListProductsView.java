package org.tagaprice.client.gwt.client.ui;

import java.util.ArrayList;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * The interface ListProductsView<T> defines the ListProducts View
 * This interface implements the interface isWidget and also defines the interface Presenter
 * which allows bidirectional communication with the ListProducts view
 * 
 * @param <T>
 */
public interface ListProductsView<T> extends IsWidget {

	public interface Presenter {
		public void goTo(Place place);

		public void onAddProduct();

		/**
		 * 
		 * @param index the item in the ArrayList<T> data at position index
		 */
		public void onEditProduct(int index);
	}

	public void setPresenter(Presenter presenter);

	public void setData(ArrayList<T> data);

}
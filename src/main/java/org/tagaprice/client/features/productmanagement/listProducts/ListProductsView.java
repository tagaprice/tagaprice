package org.tagaprice.client.features.productmanagement.listProducts;

import java.util.List;

import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.productmanagement.Product;

import com.google.gwt.place.shared.Place;

/**
 * The interface ListProductsView<T> defines the ListProducts View
 * This interface implements the interface isWidget and also defines the interface Presenter
 * which allows bidirectional communication with the ListProducts view
 * 
 * @param <T>
 */
public interface ListProductsView extends IView {

	public interface Presenter {
		public void goTo(Place place);

	}

	public void setPresenter(Presenter presenter);

	public void setData(List<Product> data);

}
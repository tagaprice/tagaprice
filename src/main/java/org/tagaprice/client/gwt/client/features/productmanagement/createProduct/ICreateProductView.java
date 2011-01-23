package org.tagaprice.client.gwt.client.features.productmanagement.createProduct;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface is necessary to implement a ProductManagementView
 * 
 */
public interface ICreateProductView extends IsWidget {

	public void setId(long id);


	/**
	 * Sets the displayed title
	 * 
	 * @param title
	 *            the displayed title
	 */
	public void setTitle(String title);

	/**
	 * Returns the currently displayed title.
	 * 
	 * @return the currently displayed title
	 */
	public String getProductTitle();



	/**
	 * Set a {@link IQuantity} in which this {@link IProduct} can be bought.
	 * 
	 * @param quantity
	 *            the {@link IQuantity} in which this {@link IProduct} can be bought.
	 */
	public void setQuantity(IQuantity quantity);

	/**
	 * Returns a list of {@link IQuantity}s corresponding to this {@link IProduct}.
	 * 
	 * @return a list of {@link IQuantity}s corresponding to this {@link IProduct}.
	 */
	public IQuantity getQuantity();

	/**
	 * Sets the depending {@link ICategory} for a
	 * {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct}
	 * 
	 * @param category
	 *            the depending {@link ICategory} for a
	 *            {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct}
	 */
	public void setCategory(ICategory category);

	/**
	 * Returns the depending {@link ICategory}
	 * 
	 * @return Returns the depending {@link ICategory}
	 */
	public ICategory getCategory();

	/**
	 * Sets the {@link Presenter} which implements the {@link IProductView} to control this view. It is also necessary
	 * for the {@link IProductView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link IProductView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

	public void setAvailableCategories(ArrayList<ICategory> categories);

	/**
	 * 
	 *
	 */
	public interface Presenter {
		/**
		 * Is used by the {@link org.tagaprice.client.gwt.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);

		/**
		 * This event is called when the user has CHANGED/CREATED a
		 * {@link org.tagaprice.client.gwt.shared.entities.productmanagement.Product}.
		 */
		public void onSaveEvent();

		/**
		 * This event is called when the user has CHANCED the title
		 * 
		 * TODO: Implement event
		 */
		public void onTitleSelectedEvent();

		/**
		 * This event is called when the user has CHANGED the Unit
		 * TODO: Implement event
		 */
		public void onUnitSelectedEvent();

		/**
		 * This event is called when the user has CHANGED the Category
		 * TODO: Implement event
		 */
		public void onCategorySelectedEvent();
	}

}

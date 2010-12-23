package org.tagaprice.client.gwt.client.features.productmanagement;

import org.tagaprice.client.gwt.shared.entities.dump.ICategory;
import org.tagaprice.client.gwt.shared.entities.dump.IUnit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface is necessary to implement a ProductManagementView
 * 
 */
public interface IProductView extends IsWidget {

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
	public String getTitle();

	/**
	 * Sets the {@link IUnit} in which this {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct}
	 * can be bought.
	 * 
	 * @param iUnit
	 *            the {@link IUnit} in which this
	 *            {@link org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct} can be bought.
	 */
	public void setUnit(IUnit iUnit);

	/**
	 * Returns the currently displayed {@link IUnit}
	 * 
	 * @return Returns the currently displayed {@link IUnit}
	 */
	public IUnit getUnit();

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

	/**
	 * 
	 *
	 */
	public interface Presenter {
		/**
		 * Is by the {@link org.tagaprice.client.gwt.client.mvp.AppActivityMapper} to display a new place in the browser window.
		 * @param place The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);

		/**
		 * This event is called when the user has CHANGED/CREATED a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.Product}.
		 * @param event is called when the user has CHANGED/CREATED a {@link org.tagaprice.client.gwt.shared.entities.productmanagement.Product}.
		 */
		public void onSaveButtonClickedEvent(ClickEvent event);

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

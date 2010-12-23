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
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 
	 * @param iUnit
	 */
	public void setUnit(IUnit iUnit);

	/**
	 * 
	 * @return
	 */
	public IUnit getUnit();

	/**
	 * 
	 * @param category
	 */
	public void setCategory(ICategory category);

	/**
	 * 
	 * @return
	 */
	public ICategory getCategory();

	/**
	 * 
	 * @param presenter
	 */
	public void setPresenter(Presenter presenter);

	/**
	 * 
	 *
	 */
	public interface Presenter {
		/**
		 * 
		 * @param place
		 */
		public void goTo(Place place);

		/**
		 * 
		 * @param event
		 */
		public void onSaveButtonClickedEvent(ClickEvent event);

		/**
		 * TODO
		 */
		public void onTitleSelectedEvent();

		/**
		 * TODO
		 */
		public void onUnitSelectedEvent();

		/**
		 * TODO
		 */
		public void onCategorySelectedEvent();
	}

}

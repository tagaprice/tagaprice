package org.tagaprice.client.gwt.client.features.productmanagement;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface is necessary to implement a ProductManagementView
 *
 */
public interface IProductView extends IsWidget {

	public void setTitle(String title);

	public String getTitle();

	//TODO
	public void setUnit();

	public void getUnit();


	public void setCategory();

	public void getCategory();


	public interface Presenter {
		public void goTo(Place place);

		public void onSaveButtonClickedEvent(ClickEvent event);

		public void onTitleSelectedEvent();
	}

}

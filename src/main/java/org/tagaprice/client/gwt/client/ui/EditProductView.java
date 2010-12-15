package org.tagaprice.client.gwt.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
/**
 * The interface EditProductView defines the EditProduct View
 * This interface implements the interface isWidget and also defines the interface presenter
 * which allows bidirectional communication with the EditProduct view
 */
public interface EditProductView extends IsWidget {

	public void setId(int id);

	public int getId();

	public void setName(String name);

	public String getName();

	public void setPrice(int price);

	public int getPrice();

	public void setDescription(String description);

	public String getDescription();

	public void setCategory(String category);

	public String getCategory();

	public void setPresenter(Presenter presenter);


	public interface Presenter {
		public void goTo(Place place);

		public void onCancelButtonClicked(ClickEvent event);

		public void onSaveButtonClicked(ClickEvent event);
	}

}

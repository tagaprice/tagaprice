package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateShopView extends IsWidget {




	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view. It is also necessary
	 * for the {@link ICreateShopView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

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
		 * shop.
		 * 
		 * @param event
		 *            is called when the user has CHANGED/CREATED a
		 *            shop.
		 */
		public void onSaveEvent();
	}

	void setStreet(String street);

	void setZip(String zip);

	void setCity(String city);

	void setCountry(Country country);

	LatLng getLatLng();


	Country getCountry();

	String getCity();

	String getZip();

	String getStreet();

	String getShopTitle();

	void setLatLng(LatLng latLng);

	void setShopTitle(String title);

	void setRevisionId(IRevisionId revisionId);
}

package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateShopView<T> extends IsWidget {

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

	String getShopTitle();

	void setShopTitle(String title);

	void setRevisionId(IRevisionId revisionId);


	/**
	 * Set some {@link IAddress} to the Shop. All include products will be deleted.
	 * @param addresses that will be set to the shop
	 */
	public void setAddresses(ArrayList<ISubsidiary> addresses);

	/**
	 * Add one new {@link IAddress} to this shop
	 * @param address that will be added to the shop
	 */
	public void addAddress(ISubsidiary address);

	/**
	 * Returns all {@link IAddress}es
	 * @return  all {@link IAddress}es
	 */
	public ArrayList<ISubsidiary> getAddresses();
}

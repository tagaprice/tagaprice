package org.tagaprice.client.gwt.client.features.shopmanagement.createShop;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.Address;
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
	 * Set current address (Position of the user)
	 * @param address address (position) of the user
	 */
	public void setCurrentAddress(Address address);


	/**
	 * Set some {@link ISubsidiary} to the Shop. All include products will be deleted.
	 * @param subsidiary that will be set to the shop
	 */
	public void setSubsidiary(ArrayList<ISubsidiary> subsidiary);

	/**
	 * Add one new {@link IAddress} to this shop
	 * @param subsidiary that will be added to the shop
	 */
	public void addSubsidiary(ISubsidiary subsidiary);

	/**
	 * Returns all {@link ISubsidiary}es
	 * @return  all {@link ISubsidiary}es
	 */
	public ArrayList<ISubsidiary> getSubsidiary();
}

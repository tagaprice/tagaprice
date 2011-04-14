package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.ArrayList;

import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.place.shared.Place;

public interface ICreateShopView<T> extends IView {

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
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
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


	/**
	 * Set current address (Position of the user)
	 * @param address address (position) of the user
	 */
	public void setCurrentAddress(Address address);


	/**
	 * Set {@link Shop} kids. All included {@link Shop} will be deleted and overwritten.
	 * @param children all {@link Shop} kids
	 */
	public void setChildren(ArrayList<Shop> children);

	/**
	 * Insert one {@link Shop} as kid
	 * @param kid {@link Shop} kid
	 */
	public void addChild(Shop child);

	/**
	 * Returns all includes {@link Shop} kids
	 * @return includes {@link Shop} kids
	 */
	public ArrayList<Shop> getChildren();
}

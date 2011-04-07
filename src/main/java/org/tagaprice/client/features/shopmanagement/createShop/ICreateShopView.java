package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.ArrayList;

import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.shopmanagement.IShop;
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

	void setRevisionId(IRevisionId revisionId);

	/**
	 * Set current address (Position of the user)
	 * @param address address (position) of the user
	 */
	public void setCurrentAddress(Address address);


	/**
	 * Set {@link IShop} kids. All included {@link IShop} will be deleted and overwritten.
	 * @param kids all {@link IShop} kids
	 */
	public void setKids(ArrayList<IShop> kids);

	/**
	 * Insert one {@link IShop} as kid
	 * @param kid {@link IShop} kid
	 */
	public void addKid(IShop kid);

	/**
	 * Returns all includes {@link IShop} kids
	 * @return includes {@link IShop} kids
	 */
	public ArrayList<IShop> getKids();
}

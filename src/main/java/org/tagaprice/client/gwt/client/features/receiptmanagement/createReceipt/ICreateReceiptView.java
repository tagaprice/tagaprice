package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateReceiptView extends IsWidget{
	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view. It is also necessary
	 * for the {@link ICreateShopView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


	public interface Presenter {

		/**
		 * Is used by the {@link org.tagaprice.client.gwt.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);
	}
}

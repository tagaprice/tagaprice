package org.tagaprice.client.features.accountmanagement.login;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ILogoutView extends IsWidget {


	/**
	 * Sets the {@link Presenter} which implements the {@link ILogoutView} to control this view. It is also necessary
	 * for the {@link ILogoutView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ILogoutView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


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
		 * This methods signals that the user has clicked the login button
		 */
		public void onLogOutEvent();

	}
}


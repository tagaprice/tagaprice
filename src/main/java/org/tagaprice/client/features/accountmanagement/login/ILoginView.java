package org.tagaprice.client.features.accountmanagement.login;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ILoginView extends IsWidget {


	/**
	 * Sets the {@link Presenter} which implements the {@link ILoginView} to control this view. It is also necessary
	 * for the {@link ILoginView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ILoginView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

	/**
	 * Returns the DisplayName of the user. This name don't have to be unique. 
	 * @return the DisplayName of the user. This name don't have to be unique. 
	 */
	public String getDisplayName();
	
	/**
	 * Returns the email address in the textbox
	 * @return email address in textbox
	 */
	public String getEmail();

	/**
	 * Returns the password in the passwordTextBox
	 * @return password in the passwordTextBox
	 */
	public String getPassword();
	
	/**
	 * Displays the wait for confirmation view.
	 */
	public void showWaitForConfirmation();
	
	
	/**
	 * Displays the SignIn/Up view. 
	 * @param showSingIn radio button is set to 'I have an account' or 'I'm new!'
	 */
	public void showSignInUp(boolean showSingIn);

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
		public void onLoginEvent();

		/**
		 * This method signals that the user has clicked the sign up button.
		 */
		public void onRegisterButtonEvent();
	}
}

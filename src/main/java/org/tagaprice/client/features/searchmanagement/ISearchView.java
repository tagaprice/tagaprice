package org.tagaprice.client.features.searchmanagement;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.place.shared.Place;


public interface ISearchView extends IsWidget {

	public interface Presenter{
		public void goTo(Place place);
	}
	
	public void setPresenter(Presenter presenter);
}

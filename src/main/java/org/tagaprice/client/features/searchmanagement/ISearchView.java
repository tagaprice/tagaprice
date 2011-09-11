package org.tagaprice.client.features.searchmanagement;

import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.place.shared.Place;


public interface ISearchView extends IsWidget {

	public interface Presenter{
		public void goTo(Place place);
		public void setAddress(Address address);
		public void onFindGpsPosition();
		public void onSearch(String searchString, BoundingBox bbox);
		
		public void onFoundPositionBySearchQuery(Address address);
	}
	
	public void setPresenter(Presenter presenter);
	
	public void setSelectableAddress(List<Address> address);
	
	public void setAddress(Address address);
	
	public void setSearchResults(List<Document> results);
}

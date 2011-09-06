package org.tagaprice.client.features.searchmanagement.desktopView;

import org.tagaprice.client.features.searchmanagement.ISearchView;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class SearchView extends Composite implements ISearchView {

	private Presenter _presenter;
	
	public SearchView() {
		// TODO Auto-generated constructor stub
		initWidget(new Label("search"));
	}
	


	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

}

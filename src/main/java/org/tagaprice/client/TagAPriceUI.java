package org.tagaprice.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;


public class TagAPriceUI implements EntryPoint {

	
	TaPManagerImpl mng = TaPManagerImpl.getInstance();
	
	public void onModuleLoad() {
		
		
		RootPanel.get().add(mng.getUIManager());
		
		//Starts the history listening on page load!
		History.fireCurrentHistoryState();
	}
}



package org.tagaprice.client.features.startmanagement.devView;

import org.tagaprice.client.features.startmanagement.IStartView;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;



public class StartViewImpl extends Composite implements IStartView {

	private Presenter _presenter;
	VerticalPanel vePa = new VerticalPanel();
	public StartViewImpl() {
		initWidget(vePa);
		final HTML fileGwtQuery = new HTML("<h1>Welcome to TagAPrice (beta)</h1>");

		vePa.add(fileGwtQuery);


		final HTML button = new HTML("<div id=\"text2\">text2</div>");
		vePa.add(button);


	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}



	@Override
	public String getInviteKey() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void setInviteKey(String inviteKey) {
		// TODO Auto-generated method stub
		
	}
	

}

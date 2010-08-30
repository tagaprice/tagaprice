package org.tagaprice.client;

import org.tagaprice.client.InfoBox.BoxType;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class InfoBoxComposite extends Composite {
	private InfoBox infoBox = new InfoBox();
	private VerticalPanel vePa1 = new VerticalPanel();
	
	public InfoBoxComposite() {
		initWidget(vePa1);
		vePa1.setWidth("100%");
		infoBox.setWidth("100%");
		infoBox.setVisible(false);
		
		//add InfoBox
		vePa1.add(infoBox);
		
	}
	
	protected void init(Widget widget){
		vePa1.add(widget);
	}
	
	protected void showInfo(Widget wid, BoxType type){
		infoBox.showInfo(wid, type);
	}
	
	protected void showInfo(String text, BoxType type){
		infoBox.showInfo(text, type);
	}
	
	protected void hideInfo() {
		infoBox.hideInfo();
	}
}

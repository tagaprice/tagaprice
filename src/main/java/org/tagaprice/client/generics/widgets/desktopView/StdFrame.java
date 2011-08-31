package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.IStdFrame;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StdFrame extends Composite implements IStdFrame{

	private VerticalPanel vePa = new VerticalPanel();
	private SimplePanel headPa = new SimplePanel();
	private SimplePanel bodyPa = new SimplePanel();
	
	public StdFrame() {
		vePa.setWidth("100%");
		vePa.add(headPa);
		vePa.add(bodyPa);
		initWidget(vePa);
		setStyleName("stdframe");
		setHeadStyleName("head");
		setBodyStyleName("body");
		
	}
	
	public void setHeader(IsWidget header){
		headPa.setWidget(header);
	}
	
	public void setBody(IsWidget body){
		bodyPa.setWidget(body);
	}
	
	@Override
	public void setStyleName(String style) {
		vePa.setStyleName("stdframe");
	}
	
	public void setHeadStyleName(String style){
		headPa.setStyleName(style);
	}
	
	public void setBodyStyleName(String style){
		bodyPa.setStyleName(style);
	}
}

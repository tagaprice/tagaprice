package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.IStdFrame;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class StdFrame extends Composite implements IStdFrame{

	private VerticalPanel vePa = new VerticalPanel();
	private HorizontalPanel headHoPa = new HorizontalPanel();
	private SimplePanel headPa = new SimplePanel();
	private SimplePanel headSavePa = new SimplePanel();
	//private SimplePanel bodyPa = new SimplePanel();
	private HorizontalPanel bodyHoPa = new HorizontalPanel();
	
	private String _style = "stdframe";
	private String _headStyle = "head";
	private String _bodyStyle = "body";
	

	private HorizontalPanel _buttonPanel = new HorizontalPanel();
	private Button _cancelButton = new Button("cancel");
	private Button _saveButton = new Button("save");
	private Button _editButton = new Button("edit");
	
	public StdFrame() {
		vePa.setWidth("100%");
		vePa.add(headHoPa);
		headHoPa.setWidth("100%");
		headPa.setHeight("30px");
		headHoPa.add(headPa);
		headHoPa.add(headSavePa);
		headHoPa.setCellWidth(headPa, "100%");
		//vePa.add(bodyPa);
		bodyHoPa.setWidth("100%");
		vePa.add(bodyHoPa);
		vePa.setCellWidth(bodyHoPa, "100%");		
		
		//buttons
		_cancelButton.setStyleName("stdButton cancel");
		_saveButton.setStyleName("stdButton save");
		_editButton.setStyleName("stdButton");

		_buttonPanel.add(_saveButton);
		_buttonPanel.add(_cancelButton);
		_buttonPanel.add(_editButton);
		headSavePa.add(_buttonPanel);
		headSavePa.setHeight("30px");
		setButtonsVisible(false);
		
		initWidget(vePa);
		setStyleName(_style);
		setHeadStyleName(_headStyle);
		setBodyStyleName(_bodyStyle);
		
		
	}
	
	public void setHeader(Widget header){
		headPa.setWidget(header);
	}
	
	public void setBody(Widget body, String width){
		body.setHeight("100%");
		SimplePanel temp = new SimplePanel(body);
		temp.setHeight("100%");
		//temp.setStyleName(_bodyStyle);
		bodyHoPa.add(temp);
		if(width!=null)
			bodyHoPa.setCellWidth(temp, width);
		
		bodyHoPa.setCellHeight(temp, "100%");
		
		setBodyStyleName(_bodyStyle);
	}
	
	public void setBody(Widget body){
		setBody(body, null);
	}
	
	
	
	@Override
	public void setStyleName(String style) {
		_style=style;
		vePa.setStyleName(_style);
	}
	
	public void setHeadStyleName(String style){
		_headStyle=style;
		headPa.setStyleName(_headStyle);
		headSavePa.setStyleName(_headStyle);
	}
	
	public void setBodyStyleName(String style){
		//TODO implement this
		
		_bodyStyle=style;
		
		for(int i=0;i<bodyHoPa.getWidgetCount();i++)
			((SimplePanel)bodyHoPa.getWidget(i)).setStyleName(_bodyStyle);

		
	}


	@Override
	public void setButtonsVisible(boolean visible) {
		headSavePa.setVisible(visible);		
	}

	@Override
	public void addCancleClickHandler(ClickHandler handler) {
		_cancelButton.addClickHandler(handler);
	}

	@Override
	public void addSaveClickHandler(ClickHandler handler) {
		_saveButton.addClickHandler(handler);		
	}

	@Override
	public void addEditClickHandler(ClickHandler handler) {
		_editButton.addClickHandler(handler);		
	}

	@Override
	public void setReadOnly(boolean readonly) {
		_cancelButton.setVisible(!readonly);
		_saveButton.setVisible(!readonly);
		_editButton.setVisible(readonly);		
	}
	
	
}

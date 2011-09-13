package org.tagaprice.client.generics.widgets.desktopView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DashboardMenuWidget extends Composite {

	public enum MENUPOINT{MYRECEIPTS, ADDRECEIPT, SETTINGS};
	private VerticalPanel _vePa = new VerticalPanel();
	private Label _myReceipts = new Label("My Receipts");
	private Label _addReceipt = new Label("add Receipt");
	private Label _setting = new Label("Settings");
	private Label _logout = new Label("Logout");
	
	public DashboardMenuWidget() {
		
		_vePa.setWidth("100%");
		
		//menu style
		_myReceipts.setStyleName("dashboardMenuItem");
		_addReceipt.setStyleName("dashboardMenuItem");
		_setting.setStyleName("dashboardMenuItem");
		_logout.setStyleName("dashboardMenuItem");
		
		_vePa.add(_myReceipts);
		_vePa.add(_addReceipt);
		_vePa.add(_setting);
		_vePa.add(_logout);
		
		//links
		_myReceipts.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				History.newItem("ListReceipts:/show");				
			}
		});
		
		_addReceipt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				History.newItem("CreateReceipt:/create");
			}
		});
		
		
		initWidget(_vePa);
	}
	
	public void setActiveType(MENUPOINT point){
		if(point.equals(MENUPOINT.ADDRECEIPT)){
			_addReceipt.setStyleName("dashboardMenuItem dashboardMenuItemActive");
		}else if(point.equals(MENUPOINT.MYRECEIPTS)){
			_myReceipts.setStyleName("dashboardMenuItem dashboardMenuItemActive");
		}else if(point.equals(MENUPOINT.SETTINGS)){
			_setting.setStyleName("dashboardMenuItem dashboardMenuItemActive");
		}
	}
	
	public void addLogoutClickHandler(ClickHandler handler){
		_logout.addClickHandler(handler);
	}
}

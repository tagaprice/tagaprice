package org.tagaprice.client.generics.widgets.desktopView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class APreviewWidget extends Composite implements HasClickHandlers {

	protected HorizontalPanel _mainHoPa = new HorizontalPanel();
	private VerticalPanel _vePaTitleRate = new VerticalPanel();
	private Image _rating = new Image("desktopView/rating3.png");
	private Label _title = new Label();
	private Image _img = new Image("desktopView/person.png");
	
	public APreviewWidget(String title, String imgUrl) {
		
		//style
		_mainHoPa.setStyleName("previewWidget");
		
		//Add image
		//_img.setSize("25px", "25px");
		_img.setStyleName("logo");
		_mainHoPa.add(_img);
		
		//title and rating
		_title.setText(title);
		_title.setStyleName("headline");
		_vePaTitleRate.setWidth("100%");
		_vePaTitleRate.add(_title);
		_vePaTitleRate.add(_rating);
		_mainHoPa.add(_vePaTitleRate);
		_mainHoPa.setCellWidth(_vePaTitleRate, "100%");
		
		_mainHoPa.setWidth("100%");
		initWidget(_mainHoPa);
		
	}
	
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
}

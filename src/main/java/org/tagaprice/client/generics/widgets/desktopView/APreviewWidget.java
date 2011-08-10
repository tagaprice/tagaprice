package org.tagaprice.client.generics.widgets.desktopView;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sun.org.apache.bcel.internal.generic.NEW;

public abstract class APreviewWidget extends Composite implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers {

	private AbsolutePanel _absolutePa = new AbsolutePanel();
	protected HorizontalPanel _mainHoPa = new HorizontalPanel();
	private VerticalPanel _vePaTitleRate = new VerticalPanel();
	private Label _title = new Label();
	private Image _img = new Image("desktopView/person.png");
	
	
	//Mouse over stuff
	protected HorizontalPanel _mainMouseHoPa = new HorizontalPanel();
	private Button _clickArea = new Button("");
	private Button _plusRating = new Button("0");
	private Button _minusRating = new Button("0");
	
		
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
		_mainHoPa.add(_vePaTitleRate);
		_mainHoPa.setCellWidth(_vePaTitleRate, "100%");
		
		_mainHoPa.setWidth("100%");
		_absolutePa.add(_mainHoPa);
		
		
		//mouse over
		//style
		_mainMouseHoPa.setStyleName("previewHoverWidget");
		
		
		_mainMouseHoPa.setWidth("100%");
		_absolutePa.add(_mainMouseHoPa);		
		_absolutePa.setWidgetPosition(_mainMouseHoPa, 0, 0);
		
		//image
		VerticalPanel imgRatingVePa = new VerticalPanel();
		imgRatingVePa.setStyleName("rating");
		
		_plusRating.setStyleName("plusRating");
		imgRatingVePa.add(_plusRating);
		_minusRating.setStyleName("minusRating");
		imgRatingVePa.add(_minusRating);
		_mainMouseHoPa.add(imgRatingVePa);
		//_mainMouseHoPa.setCellWidth(imgRatingVePa, "38px");
		
		//title
		//_mainMouseHoPa.setBorderWidth(1);
		_clickArea.setSize("100%","100%");
		_clickArea.setStyleName("clickArea");
		_mainMouseHoPa.add(_clickArea);
		_mainMouseHoPa.setCellWidth(_clickArea, "100%");
		
		
		_mainMouseHoPa.setVisible(false);
		
		initWidget(_absolutePa);
		
		
		addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent arg0) {
				_mainMouseHoPa.setVisible(true);				
			}
		});
		
		
		addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				_mainMouseHoPa.setVisible(false);					
			}
		});
		
		

		
	}
	
	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	
	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}
	
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		
		
		return _clickArea.addClickHandler(handler);
		//return addDomHandler(handler, ClickEvent.getType());
	}
}

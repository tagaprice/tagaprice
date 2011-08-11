package org.tagaprice.client.generics.widgets.desktopView;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class APreviewWidget extends Composite implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers {

	private AbsolutePanel _absolutePa = new AbsolutePanel();
	protected HorizontalPanel _mainHoPa = new HorizontalPanel();
	private VerticalPanel _vePaTitleRate = new VerticalPanel();
	private Label _title = new Label();
	private Image _img = new Image("desktopView/person.png");
	
	
	//Mouse over stuff
	protected HorizontalPanel _mainHoverHoPa = new HorizontalPanel();
	private Button _clickArea = new Button("");
	private Button _plusRating = new Button("0");
	private Button _minusRating = new Button("0");
	
	private VerticalPanel _imgRatingVePa = new VerticalPanel();
	
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
		_mainHoverHoPa.setStyleName("previewHoverWidget");
		
		
		_mainHoverHoPa.setWidth("100%");
		_absolutePa.add(_mainHoverHoPa);	
		_absolutePa.setWidgetPosition(_mainHoverHoPa, 0, 0);
		
		//image
		_imgRatingVePa.setStyleName("previewHoverWidget rating");
		
		_plusRating.setStyleName("plusRating");
		_imgRatingVePa.add(_plusRating);
		_minusRating.setStyleName("minusRating");
		_imgRatingVePa.add(_minusRating);
		_absolutePa.add(_imgRatingVePa);
		_absolutePa.setWidgetPosition(_imgRatingVePa, 0, 0);
		_imgRatingVePa.setVisible(false);
		//_mainHoverHoPa.add(imgRatingVePa);
		//_mainMouseHoPa.setCellWidth(imgRatingVePa, "38px");
		
		//title
		//_mainHoverHoPa.setBorderWidth(1);
		_clickArea.setWidth("100%");
		_clickArea.setStyleName("clickArea");
		
		
		_mainHoverHoPa.setVisible(false);
		
		initWidget(_absolutePa);
		
		
		addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent arg0) {
				_mainHoverHoPa.setVisible(true);
				_imgRatingVePa.setVisible(true);
			}
		});
		
		
		addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent arg0) {
				_mainHoverHoPa.setVisible(false);
				_imgRatingVePa.setVisible(false);
			}
		});
		
		
	}
	
	public void addHoverWidget(IsWidget widget){

		if(_mainHoverHoPa.getWidgetCount()==0){
		_mainHoverHoPa.add(_clickArea);
		_mainHoverHoPa.setCellWidth(_clickArea, "100%");
		}
		_mainHoverHoPa.add(widget);
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
		_clickArea.addClickHandler(handler);
		return _mainHoPa.addDomHandler(handler, ClickEvent.getType());
		//return _clickArea.addClickHandler(handler);
	}
}

package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.client.generics.widgets.MorphWidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class APreviewWidget extends Composite {

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
}

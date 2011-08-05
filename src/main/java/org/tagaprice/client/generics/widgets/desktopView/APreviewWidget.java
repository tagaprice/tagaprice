package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.client.generics.widgets.MorphWidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class APreviewWidget extends Composite {

	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private VerticalPanel _vePaTitleRate = new VerticalPanel();
	private Image _rating = new Image("desktopView/rating3.png");
	private Label _title = new Label();
	private Image _img = new Image("desktopView/person.png");
	
	public APreviewWidget(String title, String imgUrl) {
		
		//style
		_hoPa1.setStyleName("previewWidget");
		
		//Add image
		//_img.setSize("25px", "25px");
		_img.setStyleName("logo");
		_hoPa1.add(_img);
		
		//title and rating
		_title.setText(title);
		_title.setStyleName("headline");
		_vePaTitleRate.setWidth("100%");
		_vePaTitleRate.add(_title);
		_vePaTitleRate.add(_rating);
		_hoPa1.add(_vePaTitleRate);
		_hoPa1.setCellWidth(_vePaTitleRate, "100%");
		
		_hoPa1.setWidth("100%");
		initWidget(_hoPa1);
	}
}

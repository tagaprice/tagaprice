package org.tagaprice.client.features.startmanagement.devView;



import org.tagaprice.client.features.startmanagement.IStartView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;



public class StartViewImpl extends Composite implements IStartView {

	private Presenter _presenter;
	VerticalPanel vePa = new VerticalPanel();
	Label normalhtml = new Label("so hald");
	public StartViewImpl() {
		initWidget(vePa);
		HTML fileGwtQuery = new HTML("<h1>Welcome to TagAPrice (beta)</h1>");

		vePa.add(fileGwtQuery);

		normalhtml.setTitle("uniqutitle");
		normalhtml.setStyleName("uniqustyle");
		vePa.add(normalhtml);

		HTML button = new HTML("<div id=\"text2\">text2</div>");
		vePa.add(button);

		Button hide = new Button("hide", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				System.out.println("press");

				com.google.gwt.query.client.GQuery.$("#text2").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");

				com.google.gwt.query.client.GQuery.$("#jbuttonid").as(gwtquery.plugins.ui.Ui.Ui).button();
				System.out.println("class: "+normalhtml.getTitle());

				//$("uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
				//$("#uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
				com.google.gwt.query.client.GQuery.$(".uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
			}
		});
		vePa.add(hide);




		HTML test = new HTML("<div id=\"text\">text</div>");
		vePa.add(test);

		com.google.gwt.query.client.GQuery.$("#text").hide()
		.css("width", "400px")
		.prepend("<h1>GwtQuery Rocks !</h1>");


		//button test
		HTML jbutton = new HTML("<button id=\"jbuttonid\">jbutton</button>");
		vePa.add(jbutton);



		addAttachHandler(new Handler() {

			@Override
			public void onAttachOrDetach(AttachEvent arg0) {
				com.google.gwt.query.client.GQuery.$("h1").as(GQuery.Effects)
				.animate("backgroundColor: 'yellow'", 500)
				.delay(1000)
				.animate("backgroundColor: '#fff'", 1500);

				com.google.gwt.query.client.GQuery.$("#text").animate("color:'red', backgroundColor:'rgb(0, 128, 0)', borderColor:'#0000ff'");

				com.google.gwt.query.client.GQuery.$("#jbuttonid").as(gwtquery.plugins.ui.Ui.Ui).button();
			}
		});
	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

}

package org.tagaprice.client.features.startmanagement.desktopView;

import static com.google.gwt.query.client.GQuery.*;
import org.tagaprice.client.features.startmanagement.IStartView;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.client.generics.widgets.StdFrame;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;



public class StartViewImpl extends Composite implements IStartView {

	private Presenter _presenter;
	VerticalPanel vePa = new VerticalPanel();
	Label normalhtml = new Label("so hald");
	private StdFrame frame = new StdFrame();
	private Label title = new Label("Welcome to TagAPrice (beta)");
	public StartViewImpl() {
		initWidget(frame);
		frame.setHeader(title);
		frame.setBody(vePa);

		//vePa.add(fileGwtQuery);

		normalhtml.setTitle("uniqutitle");
		normalhtml.setStyleName("uniqustyle");
		vePa.add(normalhtml);

		final HTML button = new HTML("<div id=\"text2\">text2</div>");
		vePa.add(button);

		Button hide = new Button("Do some fancy ui-stuff", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				System.out.println("press");


				$(button).animate("color:'red'", 2000)
				.delay(1000)
				.animate("backgroundColor:'rgb(0, 128, 0)'", 1000)
				.delay(1000)
				.animate("borderColor:'#0000ff'", 2000);


				//$("uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
				//$("#uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
				$(".uniqustyle").animate("color:'red', backgroundColor:'rgb(000, 000, 128)', borderColor:'#0000ff'");
			}
		});
		vePa.add(hide);




		HTML test = new HTML("<div id=\"text\">text</div>");
		vePa.add(test);

		$(test).hide()
		.css("width", "400px")
		.prepend("<h1>GwtQuery Rocks !</h1>");



		final Button buttonj = new Button("FadeOut-In");
		vePa.add(buttonj);
		$(buttonj).as(gwtquery.plugins.ui.Ui.Ui).button();

		buttonj.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				$(buttonj)
				.slideUp()
				.slideDown()
				.delay(2000)
				.fadeOut()
				.delay(2000)
				.fadeIn(3000)
				.slideToggle(1000)
				.slideToggle(1000);

			}
		});

		final Label label2 = new Label("should slide");
		vePa.add(label2);
		Label label = new Label("Click on me and I will disappear");
		vePa.add(label);
		label.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				$((Label)arg0.getSource()).fadeOut(1000);
				$(label2).fadeOut(1000);
			}
		});


		//Button with icons
		Button button1 = new Button("Button with icon only");
		Button button2 = new Button("Button with icon on the left");
		Button button3 = new Button("Button with two icons");
		Button button4 = new Button("Button with two icons and no text");

		$(button1).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().primary("ui-icon-locked")).text(false)); //
		$(button2).as(gwtquery.plugins.ui.Ui.Ui).button("{icons: {primary: 'ui-icon-locked'}}"); //
		$(button3).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().primary("ui-icon-gear").secondary("ui-icon-triangle-1-s"))); //
		$(button4).as(gwtquery.plugins.ui.Ui.Ui).button("{icons: {primary: 'ui-icon-gear',secondary: 'ui-icon-triangle-1-s'}, text: false}");

		vePa.add(button1);
		vePa.add(button2);
		vePa.add(button3);
		vePa.add(button4);
		
		
		Button showeven = new Button("popup fadein and out after 5sec");
		vePa.add(showeven);
		
		showeven.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.fireEventTest(new InfoBoxShowEvent(StartViewImpl.class, "Info", INFOTYPE.INFO,5000));
				
			}
		});

	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}
	

}

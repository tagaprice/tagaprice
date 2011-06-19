package org.tagaprice.client.features.startmanagement.devView;

import static com.google.gwt.query.client.GQuery.*;
import org.tagaprice.client.features.startmanagement.IStartView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		final HTML fileGwtQuery = new HTML("<h1>Welcome to TagAPrice (beta)</h1>");

		vePa.add(fileGwtQuery);

		normalhtml.setTitle("uniqutitle");
		normalhtml.setStyleName("uniqustyle");
		vePa.add(normalhtml);

		final HTML button = new HTML("<div id=\"text2\">text2</div>");
		vePa.add(button);

		Button hide = new Button("Do some fancy ui-stuff", new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				System.out.println("press");

				$(fileGwtQuery).as(GQuery.Effects)
				.animate("backgroundColor: 'yellow'", 500)
				.delay(1000)
				.animate("backgroundColor: '#fff'", 1500);

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



		Button buttonj = new Button("Jbutton");
		vePa.add(buttonj);
		$(buttonj).as(gwtquery.plugins.ui.Ui.Ui).button();

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





	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}

}

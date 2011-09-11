package org.tagaprice.client.features.startmanagement.desktopView;

import org.tagaprice.client.features.startmanagement.IStartView;
import org.tagaprice.client.generics.widgets.StdFrame;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;



public class StartViewImpl extends Composite implements IStartView {

	private Presenter _presenter;
	VerticalPanel vePa = new VerticalPanel();
	Label normalhtml = new Label("so hald");
	private StdFrame frame = new StdFrame();
	private Label title = new Label("consumer-created location-aware price comparison platform");
	public StartViewImpl() {
		initWidget(frame);
		frame.setHeader(title);
		frame.setBody(new HTML("<h1>The Idea</h1>A consumer-created location-aware price comparison site.<h1>Why do we need that?</h1><ul>	<li>There is no transparent market for food.</li>	<li>The prices between shops and bars are drifting apart.</li>	<li>Extreme price differences between bars (even those close to each other)</li>	<li>The EU stopped package size standards, causing some producers to try to sell smaller packages for the same price.</li></ul><h1>What's your benefit?</h1><ul>	<li>compare prices and save money</li>	<li>finding menus for restaurants that don't put them online themselves.</li>	<li>a transparent market</li>	<li>quality and quantity comparison of shops and product. (price, ingredients, pics, user comments, labels...)</li>	<li>free household account management</li>	<li>open shop database (location-aware)</li>	<li>open product database</li>	<li>open bar code and ISBN database</li>	<li>everything has a history (it's possible to compare prices not only between shops but also over time)</li></ul><h1>How can we implement this vision?</h1><h2>You</h2><ul>	<li>enter and update products, shops</li>	<li>enter prices<ul>	<li>by typing in your receipt and add it to your household accounts</li>	<li>by taking a picture of your receipt with your mobile phone and TagAPrice will automatically add it to your household accounts</li></ul></li>	<li><strong>Donate</strong></li></ul><h2>Open Source</h2><ul>	<li>Working togehter to make TagAPrice more comfortable and feature-rich.</li>	<li>Code License: AGPLv3</li></ul><h2>Open Data</h2><ul>	<li>Create mashups and mobile apps<ul>	<li>by download a full dump of the DB</li>	<li>by using the API (json, xml, ...)</li></ul></li>	<li>Use the API to find shops (we have all shops from OpenStreetMap and maybe more)</li>	<li>Use the API to find a product at the nearest shop</li>	<li>Use the API to find the price of a product at the nearest shop</li>	<li>Use the API to find a menu and the price in a special restaurante</li>	<li>Use the API to insert the menu in your restaurant</li>	<li>Share products, shops, prices via twitter, facebook, ...</li>	<li>Data License: <a href=\"http://creativecommons.org/licenses/by-sa/3.0/\">Creative Commons Attribution-ShareAlike 3.0 Unported License</a></li></ul><h2>The Team</h2><ul>	<li>We are a team of students at Technical University of Vienna, and University Copenhagen</li>	<li>contributors from all over the world.</li></ul>"));

	}



	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;
	}
	

}

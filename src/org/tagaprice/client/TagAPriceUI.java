package org.tagaprice.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class TagAPriceUI implements EntryPoint {
	RatingWidget editable;
	RatingWidget disable;
	Label showRate = new Label("rating: 0");
	
	public void onModuleLoad() {
		VerticalPanel root = new VerticalPanel();
		root.setWidth("300px");

		
		ProductContainer[] myProducts = new ProductContainer[3];
		myProducts[0]=new ProductContainer(152, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", "260", "g",true);
		myProducts[1]=new ProductContainer(120, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", "1", "L",false);
		myProducts[2]=new ProductContainer(12, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", "2", "L",true);
		
		ReceiptContainer receiptContainer = new ReceiptContainer(
				15, 
				"Weihnachtseinkauf", 
				new Date(2010,5,15), 
				1523, 
				new ShopContainer(15, "Billa Schwedenplatz", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
				myProducts);
		
		root.add(new TitlePanel("Kasserzettel eintragen",new ReceiptWidget(receiptContainer,true)));
		
		RootPanel.get().add(root);
	}
}



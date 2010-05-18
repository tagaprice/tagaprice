package org.tagaprice.client;


import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.shared.ProductContainer;
import org.tagaprice.shared.ReceiptContainer;
import org.tagaprice.shared.ShopContainer;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class TagAPriceUI implements EntryPoint {
	RatingWidget editable;
	RatingWidget disable;
	Label showRate = new Label("rating: 0");
	
	TaPManagerImpl mng = TaPManager.getInstance();
	
	public void onModuleLoad() {
		
		/*
		VerticalPanel root = new VerticalPanel();
		root.setWidth("300px");

		
		//ProductContainer[] myProducts = new ProductContainer[3];
		ArrayList<ProductContainer> myProducts = new ArrayList<ProductContainer>();
		myProducts.add(new ProductContainer(152, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", "260", "g",true));
		myProducts.add(new ProductContainer(120, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", "1", "L",false));
		myProducts.add(new ProductContainer(12, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", "2", "L",true));
		
		ReceiptContainer receiptContainer = new ReceiptContainer(
				15, 
				"Weihnachtseinkauf", 
				new Date(2010,5,15), 
				1523, 
				new ShopContainer(15, "Billa Schwedenplatz", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
				myProducts);
		
		root.add(new TitlePanel("Kasserzettel eintragen",new ReceiptWidget(receiptContainer,true)));
		
		RootPanel.get().add(root);
		
		*/
		
		RootPanel.get().add(mng.getUIManager());
	}
}



package org.tagaprice.client;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class TagAPriceUI implements EntryPoint {
	RatingWidget editable;
	RatingWidget disable;
	Label showRate = new Label("rating: 0");
	
	TaPManager mng = TaPManagerImpl.getInstance();
	
	public void onModuleLoad() {
		
		/*
		VerticalPanel root = new VerticalPanel();
		root.setWidth("300px");

		
		//ProductData[] myProducts = new ProductData[3];
		ArrayList<ProductData> myProducts = new ArrayList<ProductData>();
		myProducts.add(new ProductData(152, "Grouda geschnitten", "logo.png", 20, 80, 325, "€", "260", "g",true));
		myProducts.add(new ProductData(120, "Ja!Natürlich Milch 1L", "logo.png", 50, 30, 98, "€", "1", "L",false));
		myProducts.add(new ProductData(12, "Coca Cola 2L", "logo.png", 50, 100, 230, "€", "2", "L",true));
		
		ReceiptData receiptData = new ReceiptData(
				15, 
				"Weihnachtseinkauf", 
				new Date(2010,5,15), 
				1523, 
				new ShopData(15, "Billa Schwedenplatz", "logo.png", 80, 50, "Flossgasse 1A", "1020 Wien", "Austria", 0.0, 0.0), 
				myProducts);
		
		root.add(new TitlePanel("Kasserzettel eintragen",new ReceiptWidget(receiptData,true)));
		
		RootPanel.get().add(root);
		
		*/
		
		RootPanel.get().add(mng.getUIManager());
		
		//Starts the history listening on page load!
		History.fireCurrentHistoryState();
	}
}



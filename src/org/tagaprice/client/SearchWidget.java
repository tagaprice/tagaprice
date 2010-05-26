package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SearchWidget extends Composite{

	private TextBox textBox;
	private VerticalPanel suggestPanel;
	private PopupPanel suggestPopup = new PopupPanel(true);
	private ListWidget<EntityPreview> suggestList; 
	private TaPManager tapManager;


	public SearchWidget(){
		tapManager= TaPManagerImpl.getInstance();
		suggestPanel = new VerticalPanel();
		textBox= new TextBox();
		suggestList = new ListWidget<EntityPreview>();

		textBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getCharCode()==KeyCodes.KEY_DOWN){
					suggestList.highlightNextSuggestion();
				} else if(event.getCharCode()==KeyCodes.KEY_UP){
					suggestList.highlightPrevSuggestion();
				}  else if(event.getCharCode()==KeyCodes.KEY_ENTER){	
					if(suggestList.getSelection()==null){
						// send search request 
					}else {
						textBox.setValue(null, false);
						suggestList.removeFromParent();
						tapManager.displayPage(new ProductPage(tapManager.getProduct(12)));
						if(suggestList.getSelection() instanceof ProductData){
							//tapManager.displayPage(new ProductPage((ProductData) suggestList.getSelection()));
						}else if(suggestList.getSelection() instanceof ShopData){
							//tapManager.displayPage(new ShopPage((ShopData) suggestList.getSelection())); 
						}
					}
				}else sendSearchRequest(textBox.getText());
			}

		});

		suggestPanel.add(textBox);		
		initWidget(suggestPanel);

		//style
		textBox.setWidth("100%");
		suggestPanel.setWidth("100%");
		suggestPopup.setWidth("400px");

		suggestPopup.setWidget(suggestList);

	}	


	public void setSuggestions(ArrayList<Entity> suggestData){
		suggestList = new ListWidget<EntityPreview>(suggestData);
		//suggestPopup.showRelativeTo(textBox);
		//suggestPanel.add(suggestList);
		suggestPopup.setWidget(suggestList);
		suggestPopup.showRelativeTo(suggestPanel);
	}

	private void sendSearchRequest(String input){
		tapManager.search(input, this);
	}



}
package org.tagaprice.client;



import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class SearchWidget extends Composite{

	protected TextBox textBox;
	protected VerticalPanel basePanel;
	protected Panel suggestPanel;
	protected TaPManager tapManager;
//	protected ListWidget<ShopPreview> shopList; 
//	protected ListWidget<ProductPreview> productList; 


	@UiConstructor
	public SearchWidget(){

		tapManager= TaPManagerImpl.getInstance();
		basePanel = new VerticalPanel();
		initWidget(basePanel);
		basePanel.setWidth("100%");
		basePanel.setWidth("400px");
		textBox = new TextBox();
		//style
		textBox.setWidth("100%"); 
		
		textBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {

				if(event.getCharCode()==KeyCodes.KEY_DOWN){
					getSuggestionList().highlightNextSuggestion();
				} else if(event.getCharCode()==KeyCodes.KEY_UP){
					getSuggestionList().highlightPrevSuggestion();
				}  else if(event.getCharCode()==KeyCodes.KEY_ENTER){	
					handleEnterKey();
				}  else sendSearchRequest(textBox.getText());

			}

		});
		
	}
	
	
	protected void addTextBox(){
		basePanel.add(textBox);
	}
	
	
	protected abstract ListWidget<? extends EntityPreview> getSuggestionList();
	
	protected abstract void handleEnterKey();
	
	protected abstract void sendSearchRequest(String searchString);
	

}
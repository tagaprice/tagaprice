package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class SearchWidget extends Composite{

	private TextBox textBox;
	private VerticalPanel basePanel;
	private VerticalPanel verticalSuggest;
	private PopupPanel popupSuggest;
	private ListWidget<EntityPreview> suggestList; 
	private TaPManager tapManager;
	public enum Filter { PRODUCT, SHOP, ANY}
	private Filter filter;



	public SearchWidget(final Filter filter){
		this.filter = filter;

		tapManager= TaPManagerImpl.getInstance();
		basePanel = new VerticalPanel();
		initWidget(basePanel);
		textBox = new TextBox();
		basePanel.add(textBox);
		suggestList = new ListWidget<EntityPreview>();

		if(filter.equals(Filter.SHOP)){
			verticalSuggest = new VerticalPanel();
			verticalSuggest.add(suggestList);
			basePanel.add(verticalSuggest);

		}else {
			popupSuggest = new PopupPanel(true);
			popupSuggest.setWidget(suggestList);
		}



		textBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {

				if(event.getCharCode()==KeyCodes.KEY_DOWN){
					suggestList.highlightNextSuggestion();
				} else if(event.getCharCode()==KeyCodes.KEY_UP){
					suggestList.highlightPrevSuggestion();
				}  else if(event.getCharCode()==KeyCodes.KEY_ENTER){	
					//fire click event in preview 
					((ProductPreview)suggestList.getSelectionPreview()).click();

				}  else sendSearchRequest(textBox.getText());
			}

		});


		//style
		textBox.setWidth("100%"); 
		basePanel.setWidth("100%");
		basePanel.setWidth("400px");


	}



	public void setSuggestions(ArrayList<Entity> suggestData){
		suggestList.populateList(suggestData);
		if(filter.equals(Filter.SHOP)){
			verticalSuggest.setVisible(true);			
		}else { 
			suggestList.addSuggestion( new NewPreview("new Product"));
			if(filter.equals(Filter.ANY))
				suggestList.addSuggestion(new NewPreview("new Shop"));
			popupSuggest.showRelativeTo(textBox);
		}

	}

	private void sendSearchRequest(String input){
		tapManager.search(input, this);
	}



}
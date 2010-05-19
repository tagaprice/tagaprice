package org.tagaprice.client;

import java.util.ArrayList;

import org.tagaprice.shared.Entity;
import org.tagaprice.shared.TaPManager;
import org.tagaprice.shared.TaPManagerImpl;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SearchWidget extends Composite{
	
	private TextBox textBox;
	private VerticalPanel suggestPanel;
	private ListWidget<EntityPreview> suggestList; 
	private TaPManager tapManager;
	
	
	public SearchWidget(){
		tapManager= TaPManagerImpl.getInstance();
		suggestPanel = new VerticalPanel();
		textBox= new TextBox();
		suggestList = new ListWidget<EntityPreview>();
		
		textBox.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				suggestList.removeFromParent();
				sendSearchRequest(textBox.getText());				
			}
		});
		
		suggestPanel.add(textBox);
		initWidget(suggestPanel);
	}	
	
	
	public void setSuggestions(ArrayList<Entity> suggestData){
		suggestList = new ListWidget<EntityPreview>(suggestData);
		suggestPanel.add(suggestList);
	}
	
	private void sendSearchRequest(String input){
		tapManager.search(input, this);
	}
	
}
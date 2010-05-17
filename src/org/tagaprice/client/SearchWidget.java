package org.tagaprice.client;

import org.tagaprice.shared.EntityContainer;



public class SearchWidget<T extends EntityContainer>{
	
	ListWidget<T> widgetList; 
	
	public SearchWidget(){
		widgetList = new ListWidget<T>();
		
	}
	
	
}
package org.tagaprice.client;



public class SearchWidget<T extends EntityContainer>{
	
	ListWidget<T> widgetList; 
	
	public SearchWidget(){
		widgetList = new ListWidget<T>();
		
	}
	
	
}
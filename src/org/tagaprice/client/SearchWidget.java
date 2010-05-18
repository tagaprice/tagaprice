package org.tagaprice.client;

import org.tagaprice.shared.Entity;



public class SearchWidget<T extends Entity>{
	
	ListWidget<T> widgetList; 
	
	public SearchWidget(){
		widgetList = new ListWidget<T>();
		
	}
	
	
}
/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: MorphWidgetErrorHandler.java
 * Date: 26.05.2010
*/
package org.tagaprice.client.widgets;

import org.tagaprice.shared.PropertyDefinition.Datatype;

public interface MorphWidgetInfoHandler {

	public void onError(Datatype errorType);
	
	public void onSuccess(Datatype errorType);
	
	public void onEmpty();
}

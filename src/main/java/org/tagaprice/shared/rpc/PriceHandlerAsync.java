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
 * Filename: PriceHandlerAsync.java
 * Date: 02.06.2010
*/
package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.PriceData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PriceHandlerAsync {

	void get(long id, BoundingBox bbox, PriceMapType type,
			AsyncCallback<ArrayList<PriceData>> callback)
		throws IllegalArgumentException;

}

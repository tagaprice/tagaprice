package org.tagaprice.client.generics.MapQuest;

import com.google.gwt.core.client.JavaScriptObject;

public class MapquestResponse extends JavaScriptObject{
	
	protected MapquestResponse() {}
	
	public final native String getLat() /*-{
		return this.lat;
	}-*/;

	public final native void setLat(String lat) /*-{
		this.lat = lat;
	}-*/;
	
	public final native String getLon() /*-{
		return this.lon;
	}-*/;
	
	public final native void setLon(String lon) /*-{
		this.lon = lon;
	}-*/;
	
	
	public final native MapquestAddress getAddress()/*-{
		return this.address;
	}-*/;
	
	public final native void setAddress(MapquestAddress address)/*-{
		this.address = address;
	}-*/;
	
	
}
package org.tagaprice.client.generics.facebook;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Class that wraps facebook Javascript SDK
 * 
 * @author ola the wrapper
 */
public class FBCore {

	/**
	 * Wrapper method
	 * @see http://developers.facebook.com/docs/reference/javascript/FB.init
	 */
	public native void init (String appId, boolean status, boolean cookie, boolean xfbml) /*-{
		$wnd.FB.init({
			'appId': appId,
			'status': status,
			'cookie': cookie,
			'xfbml' : xfbml
		});
	}-*/;


	/**
	 * Wrapper method
	 */
	public native void api (String path, AsyncCallback<JavaScriptObject> callback) /*-{
		var app=this;
		$wnd.FB.api (path, function(response){
	        app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;

	public native void api (String path, String params, AsyncCallback<JavaScriptObject> callback) /*-{
       var app=this;
       $wnd.FB.api (path, params, function(response){
           app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
       });
   }-*/;

	/**
	 * Wrapper method
	 */
	public native void api (String path, JavaScriptObject params, AsyncCallback<JavaScriptObject> callback) /*-{
		var app=this;
		$wnd.FB.api (path, params, function(response){
		    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void api (String path, String method,JavaScriptObject params, AsyncCallback<JavaScriptObject> callback) /*-{
		var app=this;
		$wnd.FB.api (path,method, params, function(response){
		    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;


	/**
	 * Wrapper method for the OLD REST API
	 */
	public native void api (JavaScriptObject params, AsyncCallback<JavaScriptObject> callback) /*-{
    	var app=this;
    	$wnd.FB.api(params,function(response){
        	app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
    	});
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void getLoginStatus (AsyncCallback<JavaScriptObject> callback) /*-{
        var app=this;
		$wnd.FB.getLoginStatus(function(response) {
            app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});

	}-*/;

	/**
	 * Wrapper method
	 */
	public native JavaScriptObject getSession () /*-{
		return $wnd.FB.getSession();
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void login (AsyncCallback<JavaScriptObject> callback) /*-{
		var app=this;
        $wnd.FB.login (function(response){
    	    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void login (AsyncCallback<JavaScriptObject> callback ,String permissions) /*-{
       	var app=this;
        $wnd.FB.login (function(response){
    	    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		},{perms:permissions});
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void logout (AsyncCallback<JavaScriptObject> callback) /*-{
		$wnd.FB.logout(function(response){
    	    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;

	/**
	 * Wrapper method
	 */
	public native void ui (JavaScriptObject params, AsyncCallback<JavaScriptObject> callback) /*-{
		var app=this;
		$wnd.FB.ui(params,function(response){
    	    app.@org.tagaprice.client.generics.facebook.FBCore::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback,response);
		});
	}-*/;

	/*
	 * Called when method succeeded.
	 */
	protected void callbackSuccess(AsyncCallback<JavaScriptObject> callback, JavaScriptObject obj) {
		callback.onSuccess (obj);
	}

}

package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserNotConfirmedException extends Exception implements IsSerializable {

	private static final long serialVersionUID = 1L;
	
	public UserNotConfirmedException(){
		super("UserNotConfirmedException");
	}
	
	public UserNotConfirmedException(String message){
		super(message);
	}
}

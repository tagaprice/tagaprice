package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InvitationKeyUsedOrInvalidException extends Exception implements IsSerializable {
	private static final long serialVersionUID = 1L;
	
	public InvitationKeyUsedOrInvalidException() {
		super("InvitationKeyUsedOrInvalidException");
	}
	
	public InvitationKeyUsedOrInvalidException(String message) {
		super(message);
	}
}

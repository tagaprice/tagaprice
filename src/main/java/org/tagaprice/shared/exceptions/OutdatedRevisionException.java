package org.tagaprice.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Thrown to indicate that at least one of the revisions of a given product is outdated, i.e. another revision of the product has been saved already.
 * @author "forste"
 *
 */
public class OutdatedRevisionException extends Exception implements IsSerializable{

	private static final long serialVersionUID = 1L;

	public OutdatedRevisionException(String message) {
		super(message);
	}

}

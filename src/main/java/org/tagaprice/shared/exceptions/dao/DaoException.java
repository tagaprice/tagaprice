package org.tagaprice.shared.exceptions.dao;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DaoException extends Exception implements IsSerializable {
	/// Default serial version ID
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}

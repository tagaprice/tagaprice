package org.tagaprice.shared.exceptions.dao;

public class TypeMismatchException extends DaoException {
	/// Default serial version ID
	private static final long serialVersionUID = 1L;

	public TypeMismatchException() {
		super();
	}

	public TypeMismatchException(String message) {
		super(message);
	}

	public TypeMismatchException(Throwable cause) {
		super(cause);
	}

	public TypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

}

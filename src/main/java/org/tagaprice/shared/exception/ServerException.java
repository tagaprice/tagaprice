package org.tagaprice.shared.exception;
/**
 * Reports to the client, that there was a problem on the server and that the user can not fix it.
 * @author Martin Weik (afraidoferrors)
 *
 */
public class ServerException extends Exception {
	private static final long serialVersionUID = -8255098943533093519L;

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(String message, Throwable cause) {
		super(message, cause);
	}
}

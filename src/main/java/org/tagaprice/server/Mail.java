package org.tagaprice.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.tagaprice.server.utilities.PropertiesFileLoader;

/**
 * Singelton that implements a mailing system.
 * 
 * Provides some convenience functions like sending a mail in one step to a recipient or using mail templates.
 */
public class Mail {
	private static Mail s_singelton;
	private Session _session;


	/**
	 * Initializes this mailing system.
	 * Loads user and password from mail.properties.
	 */
	private Mail() throws IOException {
		Properties props = new Properties();

		props.load(PropertiesFileLoader.loadResourceFile("mail.properties"));
		Authenticator auth = null;

		if (props.contains("mail.user") || props.contains("mail.password")) {
			final String user = props.getProperty("mail.user");
			final String pwd = props.getProperty("mail.password");

			auth = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pwd);
				}
			};
		}

		_session = Session.getInstance(props, auth);
	}



	/**
	 * Initialize the mailing system.
	 * Loads user and password from mail.properties
	 * 
	 * @throws IOException
	 *             NotFoundException if the property file couldn't be read
	 */
	public static void init() throws IOException {
		if (Mail.s_singelton == null) {
			Mail.s_singelton = new Mail();
		}
	}

	/**
	 * @return the current instance of null, if the singelton wasn't initialized ( see {@link Mail#init()})
	 */
	public static Mail getInstance() {
		return Mail.s_singelton;
	}

	/**
	 * Create a new message.
	 * 
	 * @see {@link Mail#send(MimeMessage)}, {@link Mail#send(String, InternetAddress, Map)},
	 *      {@link Mail#send(InternetAddress, String, String)}
	 * 
	 * @return a new {@link MimeMessage}
	 */
	public MimeMessage createMessage() {
		return new MimeMessage(_session);
	}

	/**
	 * Sends a given messages.
	 * 
	 * @see {@link Mail#createMessage()} and also {@link Mail#send(String, InternetAddress, Map)} and
	 *      {@link Mail#send(InternetAddress, String, String)}
	 * 
	 * @param msg
	 *            the message to send
	 * @throws MessagingException
	 *             throws a {@link SendFailedException} if the message could not be sent to some or any of the
	 *             recipients.
	 */
	public void send(MimeMessage msg) throws MessagingException {
		Transport.send(msg);
	}

	/**
	 * Creates and sends a new message.
	 * 
	 * @see {@link Mail#createMessage()}, {@link Mail#send(MimeMessage)} {@link Mail#send(String, InternetAddress, Map)}
	 * @param to
	 *            recipient of the message
	 * @param subject
	 *            subject of the message
	 * @param message
	 *            test of the message
	 * @throws MessagingException
	 *             throws a {@link SendFailedException} if the message could not be sent to some or any of the
	 *             recipients.
	 */
	public void send(InternetAddress to, String subject, String message) throws MessagingException {
		MimeMessage msg = createMessage();

		msg.addRecipient(Message.RecipientType.TO, to);
		msg.setSubject(subject);
		msg.setText(message);

		send(msg);
	}

	/**
	 * Creates and sends a new message using given template.
	 * 
	 * @see {@link Mail#createMessage()} and {@link Mail#send(MimeMessage)}
	 * 
	 * @param templateName
	 *            name of the template in the mailtemplates/ directory
	 * @param to
	 *            recipient of the message
	 * @param placeholderValues
	 *            replaces this key/value pairs with the keys in the mail template
	 * @throws MessagingException
	 *             throws a {@link SendFailedException} if the message could not be sent to some or any of the
	 *             recipients.
	 * @throws IOException
	 *             if the properties file could not be found
	 */
	public void send(String templateName, InternetAddress to, Map<String, String> placeholderValues)
	throws MessagingException, IOException {
		InputStream is = PropertiesFileLoader.loadResourceFile("mailtemplates/" + templateName + ".rfc822");
		byte data[] = new byte[is.available()];
		int len = is.read(data);
		String message = new String(data, 0, len);

		message = message.replace("{mail}", to.getAddress());
		Iterator<Entry<String, String>> it = placeholderValues.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			message = message.replace("{" + key + "}", value);
		}

		data = message.getBytes();
		is = new ByteArrayInputStream(data);
		MimeMessage msg = new MimeMessage(_session, is);

		send(msg);
	}
}

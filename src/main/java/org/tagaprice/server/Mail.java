package org.tagaprice.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	private static Mail instance;
	private Session session;
	
	private Mail() throws IOException {
		Properties props = new Properties();
	
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream("mail.properties");
		
		if (is == null) {
			throw new FileNotFoundException("Error: Couldn't open property file 'mail.properties'");
		}
		
		props.load(is);
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
		session = Session.getInstance(props, auth);
	}
	
	/**
	 * initialize the mailing subsystem
	 * @throws IOException NotFoundException if the property file couldn't be read
	 */
	public static void init() throws IOException {
		if (instance == null) {
			instance = new Mail();
		}
	}
	
	public static Mail getInstance() {
		return instance;
	}
	
	public MimeMessage createMessage() {
		return new MimeMessage(session);
	}

	public void send(MimeMessage msg) throws MessagingException {
		Transport.send(msg);
	}
	
	public void send(InternetAddress to, String subject, String message) throws MessagingException {
		MimeMessage msg = createMessage();
		
		msg.addRecipient(Message.RecipientType.TO, to);
		msg.setSubject(subject);
		msg.setText(message);
		
		send(msg);
	}
}

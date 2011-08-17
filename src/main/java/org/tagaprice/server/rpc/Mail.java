package org.tagaprice.server.rpc;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

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
	
	public static InputStream loadFile(String filename) throws FileNotFoundException {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream is = cl.getResourceAsStream(filename);
		if (is == null) {
			throw new FileNotFoundException("Error: Couldn't open property file '"+filename+"'");
		}
		return is;
	}
	
	private Mail() throws IOException {
		Properties props = new Properties();

		props.load(loadFile("mail.properties"));
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
	
	public void send(String tplName, InternetAddress to, Map<String, String> placeholderValues) throws MessagingException, IOException {
		InputStream is = loadFile("mailtemplates/"+tplName+".rfc822");
		byte data[] = new byte[(int) is.available()];
		int len = is.read(data);
		String message = new String(data, 0, len);

		message = message.replace("{mail}", to.getAddress());
		Iterator<Entry<String,String>>  it = placeholderValues.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> e = it.next();
			String key = e.getKey();
			String value = e.getValue();
			message = message.replace("{"+key+"}", value);
		}
		
		data = message.getBytes();
		is = new ByteArrayInputStream(data); 
		MimeMessage msg = new MimeMessage(session, is);
		
		send(msg);
	}
}
package pkg;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	public boolean sendTo(String recipient, String title, String body){
        String host = "smtp.naver.com";
        final String username = "(sender username - naver)";
        final String password = "(sender password - naver)";
        int port=465;

        Properties props = System.getProperties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", host);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un=username;
            String pw=password;
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(un, pw);
            }
        });

        session.setDebug(true);
        
        boolean success = true;

        try {
        	Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mimeMessage.setSubject(title);
            mimeMessage.setText(body);
            Transport.send(mimeMessage);
		} catch (MessagingException e) {
			success = false;
			e.printStackTrace();
		}
        
        return success;
	}
	
	public static void main(String[] args) {
		EmailSender emailSender = new EmailSender();
		emailSender.sendTo("recipient@email.com", "Hello!", "Hello World!");
	}
}

package org.ppl.net;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.ppl.core.PObject;
/**
 * 
 * @author jsb-015
 *      MailSender mail = new MailSender();
 *		mail.setText("text");
 *		mail.setSubject("测试 tomcat 发邮件 ");
 *		mail.To("vyouzhi@163.com,929398015@qq.com");
 */
public class MailSender extends PObject {
	private String Subject;
	private String Text;
	private boolean Tls = true;
	Session session;
	

	public void sendEmail(String mailaddr, RecipientType type) {

		Properties connectionProperties = new Properties();

		connectionProperties.put("mail.smtp.auth", "true");
		if (Tls) {
			connectionProperties.put("mail.smtp.starttls.enable", "true");
		}
		connectionProperties.put("mail.smtp.host",
				mailConfig.GetValue("smtp.host"));
		connectionProperties.put("mail.smtp.port",
				mailConfig.GetValue("smtp.port"));

		// Create the session
		session = Session.getDefaultInstance(connectionProperties,
				new javax.mail.Authenticator() { // Define the authenticator
					protected PasswordAuthentication getPasswordAuthentication() {
						
						return new PasswordAuthentication(mailConfig
								.GetValue("smtp.from"), mailConfig
								.GetValue("smtp.passwd"));
					}
				});

		try {
			// Create the message
			Message message = new MimeMessage(session);
			// Set sender
			message.setFrom(new InternetAddress(mailConfig.GetValue("smtp.from")));
			// Set the recipients

			message.setRecipients(type, InternetAddress.parse(mailaddr));

			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			// Set message subject
			message.setContent(Text, "text/html; charset=UTF-8");
			message.setSubject(Subject);

			// Send the message
			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void To(String mailaddr) {
		sendEmail(mailaddr, Message.RecipientType.TO);
	}

	public void BCC(String mailaddr) {
		sendEmail(mailaddr, Message.RecipientType.BCC);
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public boolean isTls() {
		return Tls;
	}

	public void setTls(boolean tls) {
		Tls = tls;
	}

}

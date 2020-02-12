package com.bridgelabz.fundoonotes.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailServiceProvider {
	@Autowired
	private static JavaMailSender javaMailsender;
	public static void sendMail(String toMail,String subject,String body)
	{
		GmailDetails gmail=new GmailDetails();
		String fromMail=gmail.getEmail();
		String password=gmail.getPassword();
		Properties prop=new Properties();//It is used to maintain list of value in which the key is a string
		//and the value is also a string
		prop.put("mail.smtp.auth","true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		//Authenticator class is used in those cases
		//where an authentication is required to visit some URL
		Authenticator auth=new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return  new PasswordAuthentication(fromMail, password);
				
			}
		};
		Session session=Session.getInstance(prop, auth);
		send(session,fromMail,toMail,subject,body);
	}
	private static void send(Session session, String fromMail, String toMail, String subject, String body) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromMail, "pradeep"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (Exception e) {
			System.out.println("exception occured while sending mail");
		}
	}

}

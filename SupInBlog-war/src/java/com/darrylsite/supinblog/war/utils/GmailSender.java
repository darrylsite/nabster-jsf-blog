package com.darrylsite.supinblog.war.utils;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;

import java.util.*;
import javax.faces.bean.ManagedBean;

/**
 * Simple Class to send an email using JavaMail API (javax.mail) and Gmail SMTP server
 * @author Dunith Dhanushka, dunithd@gmail.com
 * @version 1.0
 */
@ManagedBean
public class GmailSender {

    private static String HOST = "smtp.gmail.com";
    private static String USER = "xxxx@gmail.com";
    private static String PASSWORD = "yyyyy";
    private static String PORT = "465";
    private static String FROM = "xxx@yahoo.fr";
    private static String TO = "xxxx@darrylsite.com";

    private static String STARTTLS = "true";
    private static String AUTH = "true";
    private static String DEBUG = "true";
    private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static String SUBJECT = "";
    private static String TEXT = "";

    public static synchronized void send(String from, String to, String content, String subject)
    {
        FROM = from;
        TO = to;
        TEXT = content;
        SUBJECT = subject;
        //Use Properties object to set environment properties
        Properties props = new Properties();

        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.user", USER);

        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", STARTTLS);
        props.put("mail.smtp.debug", DEBUG);

        props.put("mail.smtp.socketFactory.port", PORT);
        props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {

            //Obtain the default mail session
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            //Construct the mail message
            MimeMessage message = new MimeMessage(session);
            message.setText(TEXT);
            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));

            message.addRecipient(RecipientType.TO, new InternetAddress(TO));
            message.saveChanges();

            //Use Transport to deliver the message
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
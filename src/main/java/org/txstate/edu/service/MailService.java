package org.txstate.edu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@PropertySource("classpath:application.properties")
@ConfigurationProperties
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${aws.ses.smtp.username}")
    private String awsSesSmtpUsername;

    @Value("${aws.ses.smtp.password}")
    private String awsSesSmtpPassword;

    @Value("${aws.ses.smtp.host}")
    private String awsSesSmtpHost;

    @Value("${aws.ses.smtp.port}")
    private int awsSesSmtpPort;

    @Value("${aws.ses.config.set}")
    private String sesConfigurationName;

    @Value("${bank.email.sender}")
    private String sender;

    @Value("${bank.email.sender.name}")
    private String senderName;

    public void sendMail(String recipient, String subject, String htmlBody, boolean aws) throws UnsupportedEncodingException, MessagingException {
        if (aws) {
            this.sendMail(recipient, subject, htmlBody);
        } else {
            final String username = System.getenv("TXBANK_EMAIL");
            final String password = System.getenv("TXBANK_PASSWORD");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setContent(htmlBody, "text/html");
                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMail(String recipient, String subject, String message) throws UnsupportedEncodingException, MessagingException {
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", awsSesSmtpPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sender, senderName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setContent(message, "text/html");

        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", sesConfigurationName);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try {
            logger.info("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(awsSesSmtpHost, awsSesSmtpUsername, awsSesSmtpPassword);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            logger.info("Email sent!");
        } catch (Exception ex) {
            logger.info("The email was not sent.");
            logger.info("Error message: " + ex.getMessage());
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
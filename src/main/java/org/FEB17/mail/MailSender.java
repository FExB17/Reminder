/**
 * deprecated
 sends mail by reading everything from config.properties
 */

package org.FEB17.mail;

import org.FEB17.utils.ConfigReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class MailSender {
    private static final Logger logger  = Logger.getLogger(MailSender.class.getName());
    private static final ConfigReader config = new ConfigReader();

    public static Properties createGmailProperties(){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public static String sendMail (String toMail, String subject, String body){
        String statusInfo;

        //TODO die daten aus dem config reader nur einmal einlesen und abspeichern damit nicht jedesmal configreader erneut initialisiert wird
        final String username = config.getProperty("username"); // Absender-Email-Adresse
        final String password = config.getProperty("password"); // Passwort oder App-spezifisches Passwort

        Properties props = createGmailProperties();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username,"ReminderApp"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail));
            message.setSubject(subject);
            message.setText(body);

            message.setHeader("X-Mailer", "ReminderApp/1.0");
            message.setHeader("Reply-To", username);


            // E-Mail senden
            Transport.send(message);
            statusInfo = "Mail successfully sent";
            logger.info(statusInfo);
            return statusInfo;
        } catch (Exception e) {
            statusInfo = "Error while sending e-mail : " + e.getMessage();
            logger.severe(statusInfo);
            return statusInfo;
        }
    }
}

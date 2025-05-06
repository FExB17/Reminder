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

    public static void sendMail(){
        ConfigReader config = new ConfigReader();

        // Empfänger und Absender-Details
        final String username = config.getProperty("username"); // Absender-Email-Adresse
        final String password = config.getProperty("password"); // Passwort oder App-spezifisches Passwort

        // Empfänger
        String toEmail = config.getProperty("toMail").replace(" ","");


        // SMTP-Server-Einstellungen
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Authentifizierung erforderlich
        props.put("mail.smtp.starttls.enable", "true"); // TLS aktivieren
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP-Server
        props.put("mail.smtp.port", "587"); // Port

        // Authentifizieren und Session erstellen
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Erstelle eine neue E-Mail-Nachricht
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Absender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Empfänger; Methode erkennt kommata automatisch

            String subject = config.getProperty("subject");
            String body = config.getProperty("body");
            message.setSubject(subject); // Betreff
            message.setText(body); // Inhalt der E-Mail

            // E-Mail senden
            Transport.send(message);

            logger.info("Mail successfully sent");

        } catch (MessagingException e) {
            logger.severe("Error while sending the mail : " + e.getMessage());
        }
    }

    public static String sendMail (String toMail, String subject, String body){
        String statusInfo;
        ConfigReader config = new ConfigReader();


        // Empfänger und Absender-Details
        final String username = config.getProperty("username"); // Absender-Email-Adresse
        final String password = config.getProperty("password"); // Passwort oder App-spezifisches Passwort


        // SMTP-Server-Einstellungen
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Authentifizierung erforderlich
        props.put("mail.smtp.starttls.enable", "true"); // TLS aktivieren
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP-Server
        props.put("mail.smtp.port", "587"); // Port

        // Authentifizieren und Session erstellen
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Erstelle eine neue E-Mail-Nachricht
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Absender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail)); // Empfänger; Methode erkennt kommata automatisch

            message.setSubject(subject); // Betreff
            message.setText(body); // Inhalt der E-Mail

            // E-Mail senden
            Transport.send(message);
            statusInfo = "successfully sent";
            logger.info(statusInfo);


        } catch (MessagingException e) {
            statusInfo = "Error while sending e-mail : " + e.getMessage();
            logger.severe(statusInfo);
        }
        return statusInfo;
    }
}

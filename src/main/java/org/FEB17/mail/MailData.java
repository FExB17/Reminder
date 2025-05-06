package org.FEB17.mail;

public class MailData {

    public final String to, subject, body;
    public final int interval;
    public MailData(String to, String subject, String body,int interval) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.interval = interval;
    }

}

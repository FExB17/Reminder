package org.FEB17.models;

public class Reminder {

    private String recipient, subject;
    private int interval;
    private Status status;

    public Reminder(String recipient, String subject, int interval, Status status) {
        this.recipient = recipient;
        this.subject = subject;
        this.interval = interval;
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

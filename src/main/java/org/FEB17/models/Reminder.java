package org.FEB17.models;

import java.util.UUID;

public class Reminder {

    private String recipient, subject, body;
    private int interval;
    private Status status;
    private final UUID id;

    public Reminder(String recipient, String subject, String body, int interval, Status status) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.interval = interval;
        this.status = status;
        this.id = UUID.randomUUID();
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

    public String getBody() {
        return body;
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

    public UUID getId() {
        return id;
    }
}

package org.FEB17.models;

import org.FEB17.mail.MailData;

import java.util.UUID;
public class Reminder {

    private String recipient, subject, body;
    private int interval;
    private Status status;
    private final UUID id;
    private final long createdAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public Reminder(MailData data, int interval) {
        this.id = UUID.randomUUID();
        this.recipient = data.to();
        this.subject = data.subject();
        this.body = data.body();
        this.interval = interval;
        this.status = Status.ACTIVE;
        this.createdAt = System.currentTimeMillis();
    }

    /**
     * Notwendig für JSON-Deserialisierung durch Gson.
     * Wird im Code nicht direkt verwendet.
     */
    @SuppressWarnings("unused")
    public Reminder() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public int getInterval() {
        return interval;
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

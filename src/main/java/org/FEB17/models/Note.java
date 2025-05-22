package org.FEB17.models;


import org.FEB17.desktop.NoteData;

import java.time.LocalDateTime;
import java.util.*;

public class Note {
    private final UUID id;
    private final LocalDateTime date;
    private String content;
    private Status status;
    private int interval;


    public Note(NoteData data, int interval) {
        this.content = data.content();
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.status = Status.ACTIVE;
        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
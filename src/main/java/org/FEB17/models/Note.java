package org.FEB17.models;


import org.FEB17.desktop.NoteData;

import java.time.LocalDateTime;
import java.util.*;

public class Note {
    private final UUID id;
    private final String createdAt;
    private String content;
    private Status status;
    private int interval;


    public Note(NoteData data, int interval) {
        this.content = data.content();
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().toString()).toString();
        this.status = Status.ACTIVE;
        this.interval = interval;
    }

    @SuppressWarnings("unused")
    public Note(){
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().toString()).toString();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void start(){
        this.status = Status.ACTIVE;
    }
    public  void stop(){
        this.status = Status.STOPPED;
    }
}
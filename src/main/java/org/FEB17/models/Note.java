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
    private String startedAt;


    public Note(NoteData data, int interval) {
        this.content = data.content();
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().toString()).toString();
        this.status = Status.ACTIVE;
        this.interval = interval;
        this.startedAt = createdAt;
    }

    @SuppressWarnings("unused")
    public Note(){
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.parse(LocalDateTime.now().toString()).toString();
    }

    public int getInterval() {
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

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt() {
        this.startedAt = LocalDateTime.parse((LocalDateTime.now().toString())).toString();
    }

    public void setStartedAt(String startedAt) {
        this.startedAt  = startedAt;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void start(){
        this.status = Status.ACTIVE;
        setStartedAt();
    }

    public  void stop(){
        this.status = Status.STOPPED;
        startedAt = "";
    }
}
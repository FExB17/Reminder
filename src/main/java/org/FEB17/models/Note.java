package org.FEB17.models;


import java.time.LocalDateTime;
import java.util.*;

public class Note {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime date = LocalDateTime.now();
    private String content;


    public Note(String content) {
        this.content = content;
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

}
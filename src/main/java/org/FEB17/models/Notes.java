package org.FEB17.models;


import java.time.LocalDateTime;
import java.util.*;

public class Notes {
    private final UUID id = UUID.randomUUID();
    private final LocalDateTime date = LocalDateTime.now();
    private String subject;


    public Notes(String subject) {

        this.subject = subject;
    }

    public UUID getId() {
        return id;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
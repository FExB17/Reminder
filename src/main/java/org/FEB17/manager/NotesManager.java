package org.FEB17.manager;

import org.FEB17.controller.NotesController;
import org.FEB17.desktop.NoteData;
import org.FEB17.models.Note;
import org.FEB17.models.Status;
import org.FEB17.persistence.NoteStorage;
import org.FEB17.scheduler.NoteScheduler;

import java.util.*;

public class NotesManager {
    public Map<UUID, Note> notes = new HashMap<>();
    public NotesController controller;

    public void setController(NotesController controller) {
        this.controller = controller;
    }

    public Note createNote(NoteData noteData) {
        Note note = new Note(noteData, noteData.interval());
        notes.put(note.getId(), note);
        NoteScheduler.scheduleNote(note);
        NoteStorage.saveNotes(notes.values());
        return note;
    }

    public void deleteNote(UUID id) {
        notes.remove(id);
        NoteScheduler.stopScheduler(id);
        NoteStorage.saveNotes(notes.values());
    }

    public Collection<Note> getAllNotes() {
        return notes.values();
    }

    public void loadFromDisk() {
        NoteStorage.loadNotes().forEach(note -> notes.put(note.getId(), note));
    }

    public void stopNote(UUID id) {
        notes.get(id).stop();
        NoteScheduler.stopScheduler(id);
        NoteStorage.saveNotes(notes.values());
    }

    public void startNote(UUID id) {
        notes.get(id).start();
        NoteScheduler.startScheduler(notes.get(id));
        NoteStorage.saveNotes(notes.values());
    }

    public void startAllActiveNotes() {
        notes.values().forEach(note -> {
            if (note.getStatus() == Status.ACTIVE) {
                startNote(note.getId());
            }
        });
    }

    public void stopAllNotes() {
        notes.values().forEach(note -> {
            if (note.getStatus() == Status.ACTIVE) {
                stopNote(note.getId());
            }
        });
    }

    public void startAllNotes() {
        notes.values().forEach(note -> {
            if (note.getStatus() == Status.STOPPED) {
                startNote(note.getId());
            }
        });
    }
    public void deleteAllNotes(){
        new ArrayList<>(getAllNotes()).forEach(note -> deleteNote(note.getId()));
    }
}

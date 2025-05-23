package org.FEB17.manager;

import org.FEB17.controller.NotesController;
import org.FEB17.desktop.NoteData;
import org.FEB17.models.Note;
import org.FEB17.scheduler.NoteScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotesManager {
    public Map<UUID, NoteData> notes = new HashMap<>();
    public NotesController controller;


    public void setController(NotesController controller) {
        this.controller = controller;
    }

    public Note createNote(NoteData noteData) {
        Note note = new Note(noteData, noteData.interval());
        notes.put(note.getId(), noteData);
        NoteScheduler.scheduleNote(note);
        return note;
    }

    public void deleteNote(UUID id) {
        notes.remove(id);
        NoteScheduler.stopScheduler(id);
    }
}

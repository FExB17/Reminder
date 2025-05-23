package org.FEB17.controller;

import org.FEB17.desktop.NoteData;
import org.FEB17.manager.NotesManager;
import org.FEB17.models.Note;
import org.FEB17.ui.NotesForm;
import org.FEB17.ui.NotesPanel;

import java.util.UUID;

public class NotesController {
    NotesManager manager;
    NotesPanel notesPanel;
    NotesForm notesForm;

    public NotesController(NotesManager manager, NotesForm notesForm, NotesPanel notesPanel) {
        this.notesForm = notesForm;
        notesForm.setController(this);
        this.notesPanel = notesPanel;
        notesPanel.setController(this);
        this.manager = manager;
        manager.setController(this);


    }

    public void createNote(NoteData noteData) {
        Note note = manager.createNote(noteData);
        addToView(note);

    }

    public void addToView(Note note) {
        notesPanel.addNoteBox(note);
    }
    public void removeFromView(UUID id) {
        notesPanel.removeNoteBox(id);
    }


    public void deleteNote(UUID id) {
        // delete from manager
        manager.deleteNote(id);

        // delete from view
        removeFromView(id);
    }
}

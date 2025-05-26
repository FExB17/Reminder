package org.FEB17.controller;

import org.FEB17.desktop.NoteData;
import org.FEB17.manager.NotesManager;
import org.FEB17.models.Note;
import org.FEB17.models.Status;
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
        notesPanel.addNoteBox(note);

    }

    public void removeFromView(UUID id) {
        notesPanel.removeNoteBox(id);
    }

    public void deleteNote(UUID id) {
        manager.deleteNote(id);
        removeFromView(id);
    }

    // initialisiert die Liste der Notizen und startet alle aktiven Notizen und sortiert sie
    public void renderAllNotes(){
        manager.loadFromDisk();
        renderSortedNotes(notesPanel.getAscending());
        manager.startAllActiveNotes();
    }

    public void stopNote(UUID id){
        manager.stopNote(id);
        notesPanel.updateViewToStopped(id);
    }

    public void startNote(UUID id){
        manager.startNote(id);
        notesPanel.updateViewToActive(id);
            }

    public void toggleNote(UUID id) {
        Note note = manager.notes.get(id);
        if (note.getStatus().equals(Status.ACTIVE)) {
            stopNote(id);
            note.stop();
            notesPanel.updateViewToStopped(id);
        } else {
            startNote(id);
            note.start();
            notesPanel.updateViewToActive(id);
        }
    }

    public void stopAllNotes() {
        manager.stopAllNotes();
        manager.getAllNotes().forEach(note -> notesPanel.updateViewToStopped(note.getId()));
    }

    public void startAllNotes() {
        manager.startAllNotes();
        manager.getAllNotes().forEach(note -> notesPanel.updateViewToActive(note.getId()));
    }

    public void deleteAllNotes() {
        manager.deleteAllNotes();
        notesPanel.removeAllNoteBoxes();
    }

    public void fillForm(Note note) {
        notesForm.fillForm(note);
    }

    public void renderSortedNotes(boolean ascending) {
        notesPanel.removeAllNoteBoxes();
        manager.getSortedByCreatedAt(ascending).forEach(notesPanel::addNoteBox);
    }
}

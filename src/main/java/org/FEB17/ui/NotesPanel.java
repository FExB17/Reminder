package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.models.Note;
import org.FEB17.utils.SettingsAccess;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class NotesPanel extends JPanel{
    JPanel notesContainer;
    NotesController controller;
    private boolean ascending = Boolean.parseBoolean(SettingsAccess.getProperty("noteIsAscending"));

    public NotesPanel() {

        this.setLayout(new BorderLayout());
        notesContainer = new JPanel();
        notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortBtn = new JButton(ascending ? "Sort ↑" : "Sort ↓");
        sortBtn.setFocusable(false);
        topPanel.add(sortBtn);
        this.add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(notesContainer);
        this.add((scrollPane), BorderLayout.CENTER);

        JButton stopAllBtn = new JButton("Stopp All");
        JButton startAllBtn = new JButton("Start All");
        JButton deleteAllBtn = new JButton("Delete All");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startAllBtn);
        buttonPanel.add(stopAllBtn);
        buttonPanel.add(deleteAllBtn);

        this.add(buttonPanel, BorderLayout.SOUTH);

        sortBtn.addActionListener(e ->   {
            ascending = !ascending;
            sortBtn.setText(ascending ? "Sort ↑" : "Sort ↓");
            controller.renderSortedNotes(ascending);

            SettingsAccess.setProperty("noteIsAscending", String.valueOf(ascending));
        });

        stopAllBtn.addActionListener(e ->
            controller.stopAllNotes()
        );

        startAllBtn.addActionListener(e ->
            controller.startAllNotes()
        );

        deleteAllBtn.addActionListener(e ->
            controller.deleteAllNotes()
        );
    }

    public void setController(NotesController controller) {
        this.controller = controller;
    }

    public void addNoteBox(Note note) {
        NoteBox noteBox = new NoteBox(note,controller);
        notesContainer.add(noteBox, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void removeNoteBox(UUID id) {
        notesContainer.remove(getNoteBox(id));
        this.revalidate();
        this.repaint();
    }

    public void removeAllNoteBoxes(){
        notesContainer.removeAll();
        this.revalidate();
        this.repaint();
    }

    public NoteBox getNoteBox(UUID id) {
        for (Component component : notesContainer.getComponents()){
            if (component instanceof NoteBox box){
                if (box.getNote().getId().equals(id))
                    return box;
            }
        }
        return null;
    }

    public void updateViewToStopped(UUID id) {
        getNoteBox(id).updateToStopped();
        this.revalidate();
        this.repaint();
    }

    public void updateViewToActive(UUID id) {
        getNoteBox(id).updateToActive();
        this.revalidate();
        this.repaint();
    }

    public boolean getAscending(){
        return ascending;
    }
}

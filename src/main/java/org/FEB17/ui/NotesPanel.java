package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.models.Note;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel{
    JPanel notesContainer;
    NotesController controller;

    public NotesPanel() {

        this.setLayout(new BorderLayout());
        notesContainer = new JPanel();
        notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortBtn = new JButton("Sort");
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


    }
    public void setController(NotesController controller) {
        this.controller = controller;
    }

    public void addNote(Note note) {
        NoteBox noteBox = new NoteBox(note);
        notesContainer.add(noteBox, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}

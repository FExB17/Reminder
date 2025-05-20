package org.FEB17.ui;

import org.FEB17.models.Note;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel{

    public NotesPanel() {

        this.setLayout(new BorderLayout());
        JPanel notesContainer = new JPanel();
        notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton sortBtn = new JButton("Sort");
        topPanel.add(sortBtn);
        this.add(topPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(notesContainer);
        this.add((scrollPane), BorderLayout.CENTER);

        JButton stopAll = new JButton("Stopp All");
        JButton startAll = new JButton("Start All");
        JButton deleteAll = new JButton("Delete All");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startAll);
        buttonPanel.add(stopAll);
        buttonPanel.add(deleteAll);

        Note note = new Note("Test note 1");
        NoteBox box = new NoteBox(note);
        notesContainer.add(box);
        Note note2 = new Note("Test note 2");
        NoteBox box2 = new NoteBox(note2);
        notesContainer.add(box2);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}

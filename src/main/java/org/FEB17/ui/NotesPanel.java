package org.FEB17.ui;

import javax.swing.*;
import java.awt.*;

public class NotesPanel extends JPanel{

    public NotesPanel() {

        JPanel notesContainer = new JPanel();
        notesContainer.setLayout(new BoxLayout(notesContainer, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(notesContainer);
        this.add((scrollPane), BorderLayout.CENTER);

        JButton stopAll = new JButton("Stopp All");
        JButton startAll = new JButton("Start All");
        JButton deleteAll = new JButton("Delete All");

        this.add(stopAll);
        this.add(startAll);
        this.add(deleteAll);
    }
}

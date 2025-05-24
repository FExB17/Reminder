package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.models.Note;
import org.FEB17.models.Status;

import javax.swing.*;
import java.awt.*;

public class NoteBox extends JPanel {
    private final Note note;
    private final JButton actionBtn;
    private final JLabel statusLabel;

    public NoteBox(Note note, NotesController controller) {
        this.note = note;
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        statusLabel = new JLabel(note.getStatus().toString());
        this.add(statusLabel);

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setBackground(null);
        contentArea.setText(note.getContent());


        this.add(contentArea);
        JTextArea infoArea = new JTextArea();
        infoArea.setText(
                "\nStarted at: " + (note.getCreatedAt()).substring(11,19)+
                "\tInterval: " + note.getInterval() + " Minutes"
        );
        infoArea.setBackground(null);
        infoArea.setEditable(false);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(infoArea, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionBtn = new JButton(note.getStatus() == Status.STOPPED ? "Start" : "Stop");
        buttonPanel.add(actionBtn);
        JButton deleteBtn = new JButton("Delete");
        buttonPanel.add(deleteBtn);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);

        deleteBtn.addActionListener(e -> controller.deleteNote(note.getId()));

        actionBtn.addActionListener(e ->controller.toggleNote(note.getId()));

    }

    public Note getNote() {
        return note;
    }

    public void updateToActive(){
        statusLabel.setText(Status.ACTIVE.toString());
        actionBtn.setText("Stop");
    }

    public void updateToStopped() {
        statusLabel.setText(Status.STOPPED.toString());
        actionBtn.setText("Start");
    }

}

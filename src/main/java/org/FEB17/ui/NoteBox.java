package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.models.Note;
import org.FEB17.models.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NoteBox extends JPanel {
    private final Note note;
    private final JButton actionBtn;
    private final JLabel statusLabel;
    private final NotesController controller;
    private final JTextArea infoArea;

    public NoteBox(Note note, NotesController controller) {
        this.controller = controller;
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


        infoArea = new JTextArea();
        infoArea.setBackground(null);
        infoArea.setEditable(false);
        updateInfoArea();

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

        statusLabel.addMouseListener(fillFormListener);
        contentArea.addMouseListener(fillFormListener);
        infoArea.addMouseListener(fillFormListener);
    }
    MouseListener fillFormListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            controller.fillForm(note);
        }
    };


    public Note getNote() {
        return note;
    }

    public void updateToActive(){
        statusLabel.setText(Status.ACTIVE.toString());
        actionBtn.setText("Stop");
        updateInfoArea();
    }

    public void updateToStopped() {
        statusLabel.setText(Status.STOPPED.toString());
        actionBtn.setText("Start");
        updateInfoArea();
    }

        //Die Formatierungen der InfoArea sind nicht optimal
    public void updateInfoArea(){
        String startedAt = note.getStartedAt();
        String started = (!startedAt.equals("-") && !startedAt.isEmpty())
                ? startedAt.substring(11,19)
                : "-";

        infoArea.setText(
                "\nCreated at: "    + (note.getCreatedAt()).substring(0,19)+
                "\nStarted at: "    + started +
                "\tInterval: "      + note.getInterval() + " Minutes"
        );

    }

}

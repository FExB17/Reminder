package org.FEB17.ui;

import org.FEB17.controller.NotesController;
import org.FEB17.models.Note;

import javax.swing.*;
import java.awt.*;

public class NoteBox extends JPanel {
    private Note note;

    public NoteBox(Note note, NotesController controller) {
        this.note = note;
        this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton actionBtn = new JButton("start/stop");
        buttonPanel.add(actionBtn);
        JButton deleteBtn = new JButton("delete");
        buttonPanel.add(deleteBtn);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setBackground(null);
        infoArea.setText(note.getContent());

        this.add(infoArea);
        this.add(buttonPanel, BorderLayout.EAST);

        deleteBtn.addActionListener(e ->{
            controller.deleteNote(note.getId());
                }
        );

    }
    public NoteBox getNoteBox(){
        return this;
    }
    public Note getNote() {
        return note;
    }
}

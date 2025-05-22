package org.FEB17.ui;

import org.FEB17.controller.NotesController;

import javax.swing.*;
import java.awt.*;

import org.FEB17.desktop.NoteData;

public class NotesForm extends JPanel {
    private NotesController controller;


    public NotesForm() {
        this.setLayout(new BorderLayout());

        JLabel textAreaLabel = new JLabel("Note");
        this.add(textAreaLabel, BorderLayout.NORTH);

        JTextArea textField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textField);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        SpinnerNumberModel model = new SpinnerNumberModel(30, 30, null, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        bottomPanel.add(spinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton startBtn = new JButton("Start");
        buttonPanel.add(startBtn);
        bottomPanel.add(buttonPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        startBtn.addActionListener(e -> {
            NoteData noteData = new NoteData(textField.getText(), (int) spinner.getValue());
            controller.createNote(noteData);
        });
    }

    public void setController(NotesController controller) {
        this.controller = controller;
    }
}

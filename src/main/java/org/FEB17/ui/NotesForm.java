package org.FEB17.ui;

import javax.swing.*;
import java.awt.*;

public class NotesForm extends JPanel {


    public NotesForm() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        JLabel textAreaLabel = new JLabel("Note");
        JTextField textField = new JTextField();
        this.add(textField);

        JSpinner spinner = new JSpinner();
        this.add(spinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton startBtn = new JButton("Start");
        buttonPanel.add(startBtn);
        JButton saveBtn = new JButton("Save");
        buttonPanel.add(saveBtn);
        this.add(buttonPanel);



    }
}
